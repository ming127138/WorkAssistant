package com.gzrijing.workassistant.util;

import com.gzrijing.workassistant.entity.BusinessByLeader;
import com.gzrijing.workassistant.entity.BusinessByWorker;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.entity.Inspection;
import com.gzrijing.workassistant.entity.Machine;
import com.gzrijing.workassistant.entity.MachineVerify;
import com.gzrijing.workassistant.entity.MachineVerifyInfo;
import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.entity.Progress;
import com.gzrijing.workassistant.entity.ReportInfo;
import com.gzrijing.workassistant.entity.Subordinate;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.entity.SuppliesVerify;
import com.gzrijing.workassistant.entity.SuppliesVerifyInfo;
import com.gzrijing.workassistant.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析和处理服务器返回的数据
 */
public class JsonParseUtils {
    /**
     * 登录解析
     *
     * @param jsonData
     * @return User
     */
    public static User getUser(String jsonData) {
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String userNo = jsonObject.getString("UserNo");
            String userName = jsonObject.getString("UserName");
            String userDept = jsonObject.getString("UserDept");
            String userSit = jsonObject.getString("UserSit");
            String userRank = jsonObject.getString("SitLeader");
            user = new User(userNo, userName, userDept, userSit, userRank);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Leader获取工程解析
     *
     * @param jsonData
     * @return List<BusinessByLeader>
     */
    public static List<BusinessByLeader> getLeaderBusiness(String jsonData) {
        List<BusinessByLeader> list = new ArrayList<BusinessByLeader>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String orderId = jsonObject.getString("FileNo");
                String type = jsonObject.getString("ConsTypeName");
                String state = jsonObject.getString("State");
                String deadline = jsonObject.getString("EstimateFinishDate").replace("/", "-");
                boolean urgent = jsonObject.getBoolean("IsUrgent");

                BusinessByLeader businessByLeader = new BusinessByLeader();
                businessByLeader.setOrderId(orderId);
                businessByLeader.setType(type);
                businessByLeader.setState(state);
                businessByLeader.setDeadline(deadline);
                businessByLeader.setUrgent(urgent);
                if (state.equals("未派工")) {
                    businessByLeader.setFlag("确认收到");
                } else {
                    businessByLeader.setFlag("派工");
                }

                List<DetailedInfo> infos = new ArrayList<DetailedInfo>();
                JSONArray jsonArray1 = jsonObject.getJSONArray("Detail");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    String key = jsonObject1.getString("key");
                    String value = jsonObject1.getString("value");
                    DetailedInfo detailedInfo = new DetailedInfo(key, value);
                    infos.add(detailedInfo);
                }
                String str = jsonObject.getString("PicUri");
                if (!str.equals("")) {
                    ArrayList<PicUrl> picUrls = new ArrayList<PicUrl>();
                    JSONArray jsonArray2 = jsonObject.getJSONArray("PicUri");
                    for (int k = 0; k < jsonArray2.length(); k++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(k);
                        String url = jsonObject2.getString("PicUri");
                        int index = url.lastIndexOf("/");
                        url = url.substring(index + 1);
                        PicUrl picUrl = new PicUrl();
                        picUrl.setPicUrl(url);
                        picUrls.add(picUrl);
                    }
                    businessByLeader.setPicUrls(picUrls);
                }
                businessByLeader.setDetailedInfos(infos);
                list.add(businessByLeader);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取被派工岗位的人员列表
     */
    public static List<Subordinate> getSubordinate(String jsonData) {
        List<Subordinate> subList = new ArrayList<Subordinate>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userNo = jsonObject.getString("UserNo");
                String userName = jsonObject.getString("UserName");
                Subordinate subordinate = new Subordinate();
                subordinate.setUserNo(userNo);
                subordinate.setName(userName);
                subList.add(subordinate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subList;
    }

    /**
     * Worker获取工程解析
     *
     * @param jsonData
     * @return List<BusinessByWorker>
     */
    public static List<BusinessByWorker> getWorkerBusiness(String jsonData) {
        List<BusinessByWorker> list = new ArrayList<BusinessByWorker>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String orderId = jsonObject.getString("FileNo");
                String type = jsonObject.getString("ConsTypeName");
                String state = jsonObject.getString("Accident");
                if (state.equals("")) {
                    state = "正常";
                }
                String deadline = jsonObject.getString("EstimateFinishDate").replace("/", "-");
                boolean urgent = jsonObject.getBoolean("IsUrgent");

                BusinessByWorker businessByWorker = new BusinessByWorker();
                businessByWorker.setOrderId(orderId);
                businessByWorker.setType(type);
                businessByWorker.setState(state);
                businessByWorker.setDeadline(deadline);
                businessByWorker.setUrgent(urgent);
                if (state.equals("正常")) {
                    businessByWorker.setFlag("确认收到");
                } else {
                    businessByWorker.setFlag(state);
                }

                List<DetailedInfo> infos = new ArrayList<DetailedInfo>();
                JSONArray jsonArray1 = jsonObject.getJSONArray("Detail");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    String key = jsonObject1.getString("key");
                    String value = jsonObject1.getString("value");
                    DetailedInfo detailedInfo = new DetailedInfo(key, value);
                    infos.add(detailedInfo);
                }
                String str = jsonObject.getString("PicUri");
                if (!str.equals("")) {
                    ArrayList<PicUrl> picUrls = new ArrayList<PicUrl>();
                    JSONArray jsonArray2 = jsonObject.getJSONArray("PicUri");
                    for (int k = 0; k < jsonArray2.length(); k++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(k);
                        String url = jsonObject2.getString("PicUri");
                        int index = url.lastIndexOf("/");
                        url = url.substring(index + 1);
                        PicUrl picUrl = new PicUrl();
                        picUrl.setPicUrl(url);
                        picUrls.add(picUrl);
                    }
                    businessByWorker.setPicUrls(picUrls);
                }
                businessByWorker.setDetailedInfos(infos);
                list.add(businessByWorker);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取关键字查询材料列表
     *
     * @param jsonDate
     * @return List<Supplies>
     */
    public static List<Supplies> getSuppliesQueries(String jsonDate) {
        List<Supplies> suppliesList = new ArrayList<Supplies>();
        try {
            JSONArray jsonArray = new JSONArray(jsonDate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("MakingNo");
                String name = jsonObject.getString("MakingName");
                String spec = jsonObject.getString("MakingSpace");
                String unit = jsonObject.getString("MakingUnit");
                Supplies supplies = new Supplies();
                supplies.setId(id);
                supplies.setName(name);
                supplies.setSpec(spec);
                supplies.setUnit(unit);
                supplies.setNum(1);
                suppliesList.add(supplies);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return suppliesList;
    }

    /**
     * 获取关键字查询机械列表
     *
     * @param jsonDate
     * @return List<Machine>
     */
    public static List<Machine> getMachineQueries(String jsonDate) {
        List<Machine> machines = new ArrayList<Machine>();
        try {
            JSONArray jsonArray = new JSONArray(jsonDate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("MakingNo");
                String name = jsonObject.getString("MachineName");
                String spec = jsonObject.getString("MakingSpace");
                String unit = jsonObject.getString("MakingUnit");
                Machine machine = new Machine();
                machine.setName(name);
                machines.add(machine);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return machines;
    }

    /**
     * 获取工程所有图片Url
     *
     * @param jsonData
     * @return
     */
    public static ArrayList<PicUrl> getImageUrl(String jsonData) {
        ArrayList<PicUrl> picUrls = new ArrayList<PicUrl>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String url = jsonObject.getString("PicUri");
                PicUrl picUrl = new PicUrl();
                picUrl.setPicUrl(url);
                picUrls.add(picUrl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return picUrls;
    }

    /**
     * 获取主工程进度
     *
     * @param jsonData
     * @return
     */
    public static ArrayList<Progress> getProgress(String jsonData) {
        ArrayList<Progress> list = new ArrayList<Progress>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String content = jsonObject.getString("TaskDetail");
                Progress progress = new Progress();
                progress.setContent(content);
                list.add(progress);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取巡检计划
     *
     * @param jsonData
     * @return
     */
    public static List<BusinessByWorker> getInspection(String jsonData) {
        List<BusinessByWorker> list = new ArrayList<BusinessByWorker>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String areaNo = jsonObject.getString("AreaNo");
                String areaName = jsonObject.getString("AreaName");
                String type = jsonObject.getString("PlanClass");
                String state = jsonObject.getString("State");
                String beginTime = jsonObject.getString("BeginTime");
                String endTime = jsonObject.getString("StopTime");
                String deadline = beginTime + "——\n" + endTime;

                BusinessByWorker businessByWorker = new BusinessByWorker();
                businessByWorker.setOrderId(areaNo + " / " + areaName);
                businessByWorker.setType(type);
                businessByWorker.setState(state);
                businessByWorker.setDeadline(deadline);
                businessByWorker.setFlag("巡检");

                ArrayList<Inspection> inspectionList = new ArrayList<Inspection>();
                JSONArray jsonArray1 = jsonObject.getJSONArray("DelatInf");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                    String No = jsonObject1.getString("FileNo");
                    String name = jsonObject1.getString("FileNm");
                    String model = jsonObject1.getString("FileClass");
                    String address = jsonObject1.getString("FileAddress");
                    String gps = jsonObject1.getString("FileGps");
                    String longitude = gps.split("，")[0];
                    String latitude = gps.split("，")[1];
                    String valveNo = jsonObject1.getString("ValveNo");
                    String valveGNo = jsonObject1.getString("ValveGNo");
                    String vfType = jsonObject1.getString("VfClass");
                    Inspection inspection = new Inspection();
                    inspection.setNo(No);
                    inspection.setName(name);
                    inspection.setModel(model);
                    inspection.setAddress(address);
                    inspection.setLongitude(Double.valueOf(longitude));
                    inspection.setLatitude(Double.valueOf(latitude));
                    inspection.setValveNo(valveNo);
                    inspection.setValveGNo(valveGNo);
                    inspection.setType(vfType);
                    inspectionList.add(inspection);
                }

                businessByWorker.setInspectionInfos(inspectionList);
                list.add(businessByWorker);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 获取机械审核信息
     *
     * @param jsonData
     * @return
     */
    public static ArrayList<MachineVerify> getMachineVerify(String jsonData) {
        ArrayList<MachineVerify> list = new ArrayList<MachineVerify>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("BillNo");
                String applicant = jsonObject.getString("SaveName");
                String useTime = jsonObject.getString("BeginDate");
                String returnTime = jsonObject.getString("EstimateFinishDate");
                String useAdress = jsonObject.getString("ProAddress");
                String remarks = jsonObject.getString("Remark");
                String state = jsonObject.getString("State");

                MachineVerify machineVerify = new MachineVerify();
                machineVerify.setId(id);
                machineVerify.setApplicant(applicant);
                machineVerify.setUseTime(useTime);
                machineVerify.setReturnTime(returnTime);
                machineVerify.setUseAdress(useAdress);
                machineVerify.setRemarks(remarks);
                machineVerify.setState(state);

                ArrayList<MachineVerifyInfo> infos = new ArrayList<MachineVerifyInfo>();
                JSONArray jsonArray1 = jsonObject.getJSONArray("MachineNeedDetail");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    String name = jsonObject1.getString("MachineName");
                    String applyNum = jsonObject1.getString("Qty");
                    String sendNum = jsonObject1.getString("SendQty");
                    MachineVerifyInfo info = new MachineVerifyInfo();
                    info.setName(name);
                    info.setApplyNum(applyNum);
                    info.setSendNum(sendNum);
                    infos.add(info);
                }

                machineVerify.setMachineVerifyInfoList(infos);
                list.add(machineVerify);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 获取材料审核信息
     *
     * @param jsonData
     * @return
     */
    public static ArrayList<SuppliesVerify> getSuppliesVerify(String jsonData) {
        ArrayList<SuppliesVerify> list = new ArrayList<SuppliesVerify>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String No = jsonObject.getString("BillNo");
                String applicant = jsonObject.getString("SaveName");
                String useTime = jsonObject.getString("UseDateTime");
                String remarks = jsonObject.getString("Remark");
                String state = jsonObject.getString("State");

                SuppliesVerify suppliesVerify = new SuppliesVerify();
                suppliesVerify.setId(id);
                suppliesVerify.setNo(No);
                suppliesVerify.setApplicant(applicant);
                suppliesVerify.setUseTime(useTime);
                suppliesVerify.setRemarks(remarks);
                suppliesVerify.setState(state);

                ArrayList<SuppliesVerifyInfo> infos = new ArrayList<SuppliesVerifyInfo>();
                JSONArray jsonArray1 = jsonObject.getJSONArray("MaterialDetail");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    String name = jsonObject1.getString("MakingName");
                    String spec = jsonObject1.getString("MakingSpace");
                    String unit = jsonObject1.getString("MakingUnit");
                    String applyNum = jsonObject1.getString("NeedQty");
                    String sendNum = jsonObject1.getString("SendQty");
                    SuppliesVerifyInfo info = new SuppliesVerifyInfo();
                    info.setName(name);
                    info.setSendNum(spec);
                    info.setUnit(unit);
                    info.setApplyNum(applyNum);
                    info.setSendNum(sendNum);
                    infos.add(info);
                }

                suppliesVerify.setSuppliesVerifyInfoList(infos);
                list.add(suppliesVerify);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 获取问题汇报信息
     * @param jsonData
     * @return
     */
    public static List<ReportInfo> getProblemReportInfo(String jsonData) {
        List<ReportInfo> list = new ArrayList<ReportInfo>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String fileNo = jsonObject.getString("FileNo");
                String content = jsonObject.getString("Reason");
                String state = jsonObject.getString("FileState").trim();
                String picNum = jsonObject.getString("PicQty");
                String reportor = jsonObject.getString("SaveName");
                String reportTime = jsonObject.getString("SaveDate");

                ReportInfo info = new ReportInfo();
                info.setId(id);
                info.setFileNo(fileNo);
                info.setContent(content);
                info.setPicNum(picNum);
                info.setReportor(reportor);
                info.setReportTime(reportTime);
                if(state.equals("停止")){
                    info.setState("0");
                }
                if(state.equals("继续")){
                    info.setState("1");
                }

                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取进度汇报信息
     * @param jsonData
     * @return
     */
    public static List<ReportInfo> getProgressReportInfo(String jsonData) {
        List<ReportInfo> list = new ArrayList<ReportInfo>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String fileNo = jsonObject.getString("FileNo");
                String content = jsonObject.getString("TaskDetail");
                String picNum = jsonObject.getString("PicQty");
                String reportor = jsonObject.getString("SaveName");
                String reportTime = jsonObject.getString("SaveDate");

                ReportInfo info = new ReportInfo();
                info.setId(id);
                info.setFileNo(fileNo);
                info.setContent(content);
                info.setPicNum(picNum);
                info.setReportor(reportor);
                info.setReportTime(reportTime);

                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


}
