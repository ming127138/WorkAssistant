package com.gzrijing.workassistant.util;

import com.gzrijing.workassistant.entity.BusinessByLeader;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析和处理服务器返回的数据
 */
public class JsonParseUtil {
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
                if (state.equals("未派发")) {
                    businessByLeader.setFlag("确认收到");
                } else {
                    businessByLeader.setFlag("派发");
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
                businessByLeader.setDetailedInfos(infos);
                list.add(businessByLeader);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


}
