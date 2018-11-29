//package com.roedeer.DbUtils;
//package thomsonreuters.iqm.discrepancy;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.context.ExternalContext;
//import javax.faces.context.FacesContext;
//
//import javax.sql.DataSource;
//
//import org.apache.avro.Protocol;
//import org.apache.avro.generic.GenericData;
//import org.apache.avro.generic.GenericRecord;
//import org.apache.avro.ipc.HttpTransceiver;
//import org.apache.avro.ipc.Transceiver;
//import org.apache.avro.ipc.generic.GenericRequestor;
//
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.python.modules.thread;
//
//import thomsonreuters.iqm.servlet.LoadProperties;
//import thomsonreuters.iqm.utils.IQMUtils;
//
//public class GetNDAData {
//
//
//    private Protocol protocol;
//    private GenericRequestor requestor;
//    private int currentBatch = 0;
//    public static List<DataBean> NDAList = null;
//    public int executeError =0;
//    public int flag = 0;
//    public Connection conn = null;
//    public String[] exchArray = { "TOR", "CVE", "CNX", "NEX", "PTX", "ALP", "CXC", "CXX", "NLB", "NEO", "OMG", "CAQ" };
//
//    public String protocolStr =
//            " {  \"namespace\":\"NirdRead\",  \"doc\":\"This outlines all read operations through the NDA IQM direct link.\",  " +
//                    "\"protocol\":\"messageProtocol\",  \"name\":\"Read\",  \"types\":[       {                \"name\":\"getQuotePermIdInput\",          " +
//                    "\"type\":\"record\",         \"fields\":[                                                        {\"name\":\"ric\",\"type\": \"string\"}, " +
//                    "                                                {\"name\":\"application\", \"type\": \"string\"}                    " +
//                    "                                  ]              },    {      \"name\":\"getCMEInput\",      \"type\":\"record\",  " +
//                    "    \"fields\":[ {\"name\":\"rcs\", \"type\": {\"type\": \"array\", \"items\": \"long\"}},                               " +
//                    "           {\"name\":\"mic\", \"type\": {\"type\": \"array\", \"items\": \"long\"}},                                        " +
//                    "  {\"name\":\"startRowNum\", \"type\": \"long\"},                                             {\"name\":\"endRowNum\", \"type\": \"long\"},  " +
//                    "                                             {\"name\":\"application\", \"type\": \"string\"}                                      ]    },  " +
//                    "     {      \"name\":\"getQuotePermIdOutput\",      \"type\":\"record\",      \"fields\":[                                                   {\"name\":\"ric\", \"type\":[\"null\",\"string\"],\"default\": null},                                                              {\"name\":\"permId\", \"type\":[\"null\",\"string\"],\"default\": null},                                       {\"name\":\"errCode\", \"type\": \"long\"},                                          {\"name\":\"errText\", \"type\": \"string\"}                             ]    },    {      \"name\":\"getServerTimeInput\",      \"type\":\"record\",      \"fields\":[                                          {\"name\":\"application\", \"type\": \"string\"}                                    ]    },    {      \"name\":\"CMEOutput\",      \"type\":\"record\",      \"fields\":[ {\"name\":\"ric\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"mic\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"root\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"ff_contract_date\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"ff_ticker_symbol\", \"type\":[\"null\",\"string\"],\"default\": null},                                                   {\"name\":\"ff_trading_symbol\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"first_trading_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"last_trading_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"first_delivery_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                  {\"name\":\"last_delivery_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                  {\"name\":\"series_desc\", \"type\":[\"null\",\"string\"],\"default\": null},                                              {\"name\":\"settlement_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                      {\"name\":\"initial_inventory_due_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"first_intention_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                {\"name\":\"last_intention_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                {\"name\":\"first_notice_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                     {\"name\":\"last_notice_day\", \"type\":[\"null\",\"string\"],\"default\": null},                                                      {\"name\":\"exch\", \"type\":[\"null\",\"string\"],\"default\": null}                                ]    },    {      \"name\":\"getFullLoadInput\",      \"type\":\"record\",      \"fields\":[ {\"name\":\"tableName\", \"type\": \"string\"},                                            {\"name\":\"startId\", \"type\": [\"null\",\"long\"],\"default\":null},                                           {\"name\":\"step\", \"type\": \"long\"},                                                 {\"name\":\"application\", \"type\": \"string\"},                                                       {\"name\":\"logging\", \"type\": [\"null\",\"long\"],\"default\":null}                              ]    },   {      \"name\":\"FullLoadEntryType\",      \"type\":\"record\",      \"fields\":[                          {\"name\":\"ndaId\", \"type\":\"string\"},                                              {\"name\":\"ndaPi\", \"type\":\"string\"},                                                           {\"name\":\"proNdaId\", \"type\":\"string\"},                                      {\"name\":\"proValue\", \"type\":[\"null\",\"string\"],\"default\": null},                                                    {\"name\":\"effectiveFrom\", \"type\":[\"null\",\"string\"],\"default\": null},                                          {\"name\":\"effectiveTo\", \"type\":[\"null\",\"string\"],\"default\": null},                                              {\"name\":\"eType\", \"type\":\"string\"}                                 ]    },    {      \"name\":\"getFullLoadOutput\",      \"type\":\"record\",      \"fields\":[ {\"name\":\"result\", \"type\":[\"null\",{\"type\":\"array\",\"items\":\"FullLoadEntryType\"}],\"default\": null},                                                 {\"name\":\"errCode\", \"type\":\"long\"},                                          {\"name\":\"errText\", \"type\":\"string\"}                               ]    },    {      \"name\":\"getCMEOutput\",      \"type\":\"record\",      \"fields\":[ {\"name\":\"result\", \"type\":[\"null\",{\"type\":\"array\",\"items\":\"CMEOutput\"}],\"default\": null},                                              {\"name\":\"count\", \"type\":[\"null\",\"long\"],\"default\": null},                                            {\"name\":\"errCode\", \"type\":\"long\"},                                           {\"name\":\"errText\", \"type\":\"string\"}                            ]    },    {      \"name\":\"getServerTimeOutput\",      \"type\":\"record\",      \"fields\":[ {\"name\":\"serverTime\", \"type\":[\"null\",\"string\"]},                                             {\"name\":\"errCode\", \"type\":\"long\"},                                          {\"name\":\"errText\", \"type\":\"string\"}                               ]    },    {      \"name\":\"DiscrepacyOutput\",      \"type\":\"record\",      \"fields\":[ {\"name\":\"create_date\", \"type\":\"string\"},                                                           {\"name\":\"ric\", \"type\":[\"null\",\"string\"]},                                                              {\"name\":\"perm_id\", \"type\":[\"null\",\"string\"]},                                      {\"name\":\"admin_status\", \"type\":[\"null\",\"string\"]},                                                         {\"name\":\"short_sell_restriction\", \"type\":[\"null\",\"string\"]},                                                          {\"name\":\"china_connect_eligible\", \"type\":[\"null\",\"string\"]},                                                       {\"name\":\"china_special_treatment\", \"type\":[\"null\",\"string\"]},                                                    {\"name\":\"common_name\", \"type\":[\"null\",\"string\"]},                                                     {\"name\":\"ticker_symbol\", \"type\":[\"null\",\"string\"]},                                                        {\"name\":\"trading_symbol\", \"type\":[\"null\",\"string\"]}                            ]    },               {      \"name\":\"getDiscrepacyOutput\",      \"type\":\"record\",      \"fields\":[ {\"name\":\"result\",  \"type\": {\"type\": \"array\", \"items\": \"DiscrepacyOutput\"}},                                                     {\"name\":\"errCode\", \"type\":\"long\"},                                          {\"name\":\"errText\", \"type\":\"string\"}                               ]    },               {      \"name\":\"getDiscrepacyInput\",      \"type\":\"record\",      \"fields\":[                          {\"name\":\"exch\", \"type\": \"string\"},                                              {\"name\":\"application\", \"type\": \"string\"}                             ]    },    {      \"name\":\"IncrementalOutput\",      \"type\":\"record\",      \"fields\":[                                                         {\"name\":\"master_sequence\", \"type\":[\"null\",\"string\"],\"default\": null},                                                   {\"name\":\"slave_sequence\", \"type\":[\"null\",\"string\"],\"default\": null},                                                     {\"name\":\"ndaPi\",\"type\":[\"null\",\"string\"],\"default\": null},                                                         {\"name\":\"proNdaId\", \"type\":[\"null\",\"string\"],\"default\": null},                                                  {\"name\":\"proValue\", \"type\":[\"null\",\"string\"],\"default\": null},                                                  {\"name\":\"effectiveFrom\", \"type\":[\"null\",\"string\"],\"default\": null},                                         {\"name\":\"effectiveTo\", \"type\":[\"null\",\"string\"],\"default\": null},                                                            {\"name\":\"eType\", \"type\":[\"null\",\"string\"],\"default\": null},                                                       {\"name\":\"action\", \"type\":[\"null\",\"string\"],\"default\": null}                             ]    },    {      \"name\":\"getIncrementalInput\",      \"type\":\"record\",      \"fields\":[                       {\"name\":\"startSequence\", \"type\": \"long\"},                                              {\"name\":\"application\", \"type\": \"string\"}                           ]    },    {      \"name\":\"getIncrementalOutput\",      \"type\":\"record\",      \"fields\":[ {\"name\":\"result\",  \"type\": {\"type\": \"array\", \"items\": \"IncrementalOutput\"}},                                             {\"name\":\"errCode\", \"type\":\"long\"},                                          {\"name\":\"errText\", \"type\":\"string\"}                              ]    }  ],  \"messages\":{   \"getQuotePermId\":{      \"doc\":\"get Quote Perm Id existing in NDA\",      \"request\":[ { \"name\":\"req\", \"type\":\"getQuotePermIdInput\" } ],      \"response\":\"getQuotePermIdOutput\"    },    \"getCME\":{      \"doc\":\"get CME data existing in NDA\",      \"request\":[ { \"name\":\"req\", \"type\":\"getCMEInput\" } ],      \"response\":\"getCMEOutput\"    },    \"getServerTime\":{      \"doc\":\"get the current time from the remote NDA DB server\",      \"request\":[ { \"name\":\"req\", \"type\":\"getServerTimeInput\" } ],      \"response\":\"getServerTimeOutput\"    },            \"getFullLoad\":{      \"doc\":\"get the full load table remote NDA DB server\",      \"request\":[ { \"name\":\"req\", \"type\":\"getFullLoadInput\" } ],      \"response\":\"getFullLoadOutput\"    },   \"getDiscrepacyData\":{      \"doc\":\"get the full discrepacy data source in NDA DB server\",      \"request\":[ { \"name\":\"req\", \"type\":\"getDiscrepacyInput\" } ],      \"response\":\"getDiscrepacyOutput\"    },               \"getIncrementalData\":{      \"doc\":\"get the incremental data after NDA full load\",      \"request\":[ { \"name\":\"req\", \"type\":\"getIncrementalInput\" } ],      \"response\":\"getIncrementalOutput\"    }  }}";
//
//    public String AddCurrentDate() {
//        return " AND TRUNC(SYSDATE,'MI')>= TRUNC(EFFECTIVE_FROM,'MI') AND TRUNC(SYSDATE,'MI') < TRUNC(EFFECTIVE_TO,'MI') ";
//    }
//    public GetNDAData(Connection conn) {
//        try {
//            this.conn = conn;
//            this.init();
//        } catch (SQLException e) {
//
//        }
//    }
//
//
//    public boolean checkBatch() throws SQLException {
//        boolean value = false;
//        int last_batch = 0;
//        int current_batch = 0;
//        Statement st = null;
//        ResultSet rs = null;
//        try {
//            st = this.conn.createStatement();
//            rs =
//                    st.executeQuery("SELECT NVL(MAX(BATCH),0) AS LAST_BATCH,TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMMDD')) AS CURRENT_BATCH FROM IQM_TRACE.DISCREPANCY_REPORT_TIME");
//            while (rs.next()) {
//                last_batch = rs.getInt("LAST_BATCH");
//                current_batch = rs.getInt("CURRENT_BATCH");
//                this.currentBatch = current_batch;
//                if (last_batch == current_batch) {
//                    value = true;
//                }
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (st != null) {
//                st.close();
//            }
//        }
//        return value;
//    }
//
//    public Connection getIQMConnection() throws ClassNotFoundException, SQLException {
//        Connection conn = null;
//        DataSource ds = IQMUtils.getDataSource();
//        if (ds != null) {
//            conn = ds.getConnection();
//        } else {
//            System.err.println("DiscrepancyScanTask: Could not get DataSource ~~");
//        }
//        return conn;
//    }
//
//    public int insertNDAData(String sql, DataBean bean, int batch) throws SQLException {
//        int result = 0;
//        PreparedStatement ps = null;
//        try {
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, bean.getCreate_date());
//            ps.setString(2, bean.getRic());
//            ps.setString(3, bean.getPerm_id());
//            ps.setString(4, bean.getAdmin_status());
//            ps.setString(5, bean.getShort_sell_restriction());
//            ps.setString(6, bean.getChina_connect_eligible());
//            ps.setString(7, bean.getChina_special_treatment());
//            ps.setString(8, bean.getCommon_name());
//            ps.setString(9, bean.getTicker_symbol());
//            ps.setString(10, bean.getTrading_symbol());
//            ps.setLong(11, batch);
//            try {
//                result = ps.executeUpdate();
//            } catch (Exception e) {
//                System.out.println("insertNDAData method Exception:" + e.getMessage());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (ps != null) {
//                ps.close();
//            }
//        }
//        return result;
//    }
//
//    public int DiscrepancyExecute(String sql) throws SQLException {
//        int result = 0;
//        Statement st = null;
//        try {
//            st = conn.createStatement();
//            System.out.println("[DiscrepancyExecute]:"+sql);
//            result = st.executeUpdate(sql);
//            conn.commit();
//        } catch (SQLException e) {
//            executeError = 1;
//            String msg = e.getMessage();
//            if (msg.length() > 3500) {
//                msg = msg.substring(0, 3500);
//            }
//            this.insertNavLog("ERROR","Execute SQL["+sql+"] error:["+msg+"].", "RPC");
//            e.printStackTrace();
//        } finally {
//            if (st != null) {
//                st.close();
//            }
//        }
//        return result;
//    }
//    public String getRefConfig(String name) throws SQLException {
//        String url = null;
//        Statement st = null;
//        ResultSet rs = null;
//        List<String> list = new ArrayList<String>();
//        try {
//            st = conn.createStatement();
//            String sql = "SELECT VALUE FROM IQM_REF.iqm_nav_config where NAME='" + name + "' AND FLAG='Y'";
//            rs = st.executeQuery(sql);
//            while (rs.next()) {
//                url = rs.getString("value");
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally{
//            if(rs!=null){
//                rs.close();
//            }
//            if(st!=null){
//                st.close();
//            }
//        }
//
//        return url;
//    }
//
//    /*
//    * If today's batch begin log is exist ,don't insert another begin log
//    * return today's begin log count
//    * */
//    public int GetTodayBatchCount() throws SQLException {
//        int counts = 0;
//        Statement st = null;
//        ResultSet rs = null;
//        try {
//            st = conn.createStatement();
//            String sql = "SELECT COUNT(1) FROM IQM_TRACE.IQM_NAV_LOG where LOG_COMPOSITE_NAME='IQM_NAV_UI_APP' " +
//                    "AND TRUNC(LOG_TIMESTAMP) = TRUNC(SYSDATE)" ;
//            rs = st.executeQuery(sql);
//            while (rs.next()) {
//                counts = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally{
//            if(rs!=null){
//                rs.close();
//            }
//            if(st!=null){
//                st.close();
//            }
//        }
//
//        return counts;
//    }
//    public int insertNavLog(String logtype, String msg, String title) throws SQLException {
//        //Connection conn = null;
//        Statement st = null;
//        int size = 0;
//        try {
//            //conn = this.getIQMConnection();
//            st = conn.createStatement();
//            if(msg!=null){
//                msg =msg.replaceAll("'", "''");
//            }
//            String sql =
//                    "INSERT INTO IQM_TRACE.IQM_NAV_LOG(LOG_ID,LOG_TYPE,LOG_COMPOSITE_NAME,LOG_ACTIVITY_NAME,log_user,LOG_TITLE,LOG_DESC,LOG_CLASS,LOG_TIMESTAMP)" +
//                            " VALUES(IQM_TRACE.IQM_SUNG_ASTECBAR_FEED_LOG_SEQ.NEXTVAL,'" + logtype +
//                            "','IQM_NAV_UI_APP','Class GetNDAData getData()','IQM_NAV','" + title + "','" + msg + "',1,sysdate)";
//            System.out.println("sql::"+sql);
//            size = st.executeUpdate(sql);
//
//            conn.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (st != null) {
//                st.close();
//            }
//        }
//        return size;
//    }
//    public int insertExchangeNavLog(String logtype, String msg, String title,String exchange_name) throws SQLException {
//        //Connection conn = null;
//        Statement st = null;
//        int size = 0;
//        try {
//            //conn = this.getIQMConnection();
//            st = conn.createStatement();
//            String sql =
//                    "INSERT INTO IQM_TRACE.IQM_NAV_LOG(LOG_ID,LOG_TYPE,LOG_COMPOSITE_NAME,LOG_ACTIVITY_NAME,log_user,LOG_TITLE,LOG_FILE_TYPE,LOG_DESC,LOG_CLASS,LOG_TIMESTAMP)" +
//                            " VALUES(IQM_TRACE.IQM_SUNG_ASTECBAR_FEED_LOG_SEQ.NEXTVAL,'" + logtype +
//                            "','IQM_NAV_UI_APP','Class GetNDAData getData()','IQM_NAV','" + title + "','"+exchange_name+"','"+msg+ "',1,sysdate)";
//            System.out.println("sql::"+sql);
//            size = st.executeUpdate(sql);
//
//            conn.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (st != null) {
//                st.close();
//            }
//        }
//        return size;
//    }
//
//    private void init() throws SQLException {
//        Transceiver t = null;
//        try {
//            // protocol = Protocol.parse(new File("C:\\Users\\U6042038\\AppData\\Roaming\\JDeveloper\\system12.1.3.0.41.140521.1008\\o.j2ee\\drs\\IQMNativeMasteringDemo\\iqmWebApp.war\\read.protocol"));
//            File newFile = new File("read.protocol");
//            byte bytes[] = new byte[512];
//            bytes = protocolStr.getBytes();
//            int b = protocolStr.length();
//            FileOutputStream fos = new FileOutputStream(newFile);
//            fos.write(bytes, 0, b);
//            fos.close();
//            System.out.println("new read.protocol path:" + newFile.getAbsolutePath());
//            protocol = Protocol.parse(newFile);
//            //protocol = Protocol.parse(new File(this.projectPath+"\\discrepancy\\read.protocol"));
////             ((SysConfig) BeanFactory.getBean("sysConfig")).getServer_ip()
//            //port 9527 read ,9528 write 10.35.24.185
//            ///String url = "http://U6032014-TPL-A:9527";
//            // Transceiver t = new HttpTransceiver(new URL(url));
//            String url = this.getRefConfig("RPC");
//            if(url!=null){
//                flag =1;
//                int batchCount = this.GetTodayBatchCount();
//                if(batchCount==0){
//                    this.insertNavLog("INFO", "begin calling RPC", "RPC");
//                }
//                try{
//                    System.out.println("Transceiver BEGIN:"+t);
//                    t = new HttpTransceiver(new URL(url));
//                    System.out.println("Transceiver:"+t);
//                }catch(Exception e){
//                    this.insertNavLog("ERROR", "calling RPC ERROR,the server url:"+url+" is not accessable.", "RPC");
//                }
//                requestor = new GenericRequestor(protocol, t);
//                System.out.println("init requestor::"+requestor);
//            }
//            else{
//                flag =0;
//                this.insertNavLog("ERROR", "The server url to get NDA data is null OR server url config flag status is N.", "RPC");
//            }
//
//        } catch (IOException e) {
//            System.out.println("the configuration to get nda data is not correct,please check the ip address and access port");
//            e.printStackTrace();
//        }
//        finally{
//            if(t!=null){
//                try {
//                    t.close();
//                } catch (IOException e) {
//                }
//                t = null;
//            }
//        }
//
//    }
//
//
//    public GenericData.Record scratch(String exch) {
//        Object outputData = sendMessage(exch);
//        if (outputData != null) {
//            return analyzeOutput(outputData);
//        }
//        System.out.println("There is no record from NDA");
//        return null;
//    }
//
//    private Object sendMessage(String exch) {
//        GenericRecord inputData = new GenericData.Record(protocol.getType("getDiscrepacyInput"));
//        inputData.put("exch", exch);
//        inputData.put("application", "Get All NDA Entities value");
//        //inputData.put("logging", "false");
//        GenericRecord requestData =
//                new GenericData.Record(protocol.getMessages().get("getDiscrepacyData").getRequest());
//        requestData.put("req", inputData);
//
//        try {
//            System.out.println("requestor:"+requestor);
//            if(requestor!=null){
//                Object result = requestor.request("getDiscrepacyData", requestData);
//                return result;
//            }
//            return null;
//        } catch (Exception e) {
//            try {
//                this.insertNavLog("ERROR", exch+" sendMessage error:[" + e.getMessage() + "].", "RPC");
//            } catch (SQLException f) {
//            }
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    private GenericData.Record analyzeOutput(Object result) {
//        if (result instanceof GenericData.Record) {
//            GenericData.Record record = (GenericData.Record) result;
//            Long errCode = new Long(record.get("errCode").toString());
//            //loggerInstance.log(Level.INFO, record.get("errText").toString());
//            if (errCode == 0) {
//                System.out.println("Get records from nda successfully");
//            }
//
//            return record;
//        }
//
//        return null;
//    }
//
//    public void CreateReport(int batch) throws SQLException {
//        String C_exchange = null;
//        for(int i =0;i<exchArray.length;i++){
//            C_exchange = exchArray[i];
//            String aSql =
//                    "INSERT INTO IQM_TRACE.DISCREPANCY_REPORT_A(ID,PERMID,PRO_NAME_D,PRO_IQM_ID,IQM_VALUE,NDA_VALUE,EXCHANGE_NAME,BATCH,RIC,CREATE_TIME) " +
//                            " SELECT IQM_TRACE.DISCREPANCY_REPORT_A_SEQ.NEXTVAL,PERMID,PRO_NAME_D,PRO_IQM_ID,IQM_VALUE,NDA_VALUE,EXCHANGE_NAME,BATCH,RIC,sysdate FROM IQM_TRACE.DISCREPANCY_REPORT_VIEW_A VIEWA WHERE EXCHANGE_NAME='"+C_exchange+"' AND BATCH= " +
//                            batch;
//            this.DiscrepancyExecute(aSql);
//        }
//        for(int i =0;i<exchArray.length;i++){
//            C_exchange = exchArray[i];
//            String bSql =
//                    "INSERT INTO IQM_TRACE.DISCREPANCY_REPORT_BC(ID,IQM_PERMID,NDA_PERMID,IQM_RIC,NDA_RIC,EXCHANGE_NAME,BATCH,EXIST_TYPE,CREATE_TIME) " +
//                            " SELECT IQM_TRACE.DISCREPANCY_REPORT_BC_SEQ.NEXTVAL,IQM_PERMID,NDA_PERMID,IQM_RIC,NDA_RIC,EXCHANGE_NAME,BATCH,TYPE,sysdate FROM IQM_TRACE.DISCREPANCY_REPORT_VIEW_BC VIEWBC WHERE EXCHANGE_NAME='"+C_exchange+"' AND BATCH = " +
//                            batch;
//            this.DiscrepancyExecute(bSql);
//        }
//        String updatebatchSql =
//                "update IQM_TRACE.DISCREPANCY_REPORT_TIME set end_time = sysdate where end_time is null and batch =" +
//                        batch;
//        //ending generate report progressing successful with no errors then insert the end message
//        if(executeError==0){
//            this.DiscrepancyExecute(updatebatchSql);
//            this.insertNavLog("INFO","end calling RPC", "RPC");
//        }
//
//    }
//
//    public void getData() throws SQLException {
//        boolean checkBatch = this.checkBatch();
//        //1:check batch if today's report is generated,then exit
//        //2:if today's report is not generated ,then insert batch log(DISCREPANCY_REPORT_TIME) first,then access NDA data by every exchange
//        //3:transform the nda json data to java DataBean,and insert into the DB temporal table DISCREPANCY_DATA using sql row by row
//        //4:use sql to query the discrepancy between iqm and nda data then insert into report table
//        //5:update the batch log table(DISCREPANCY_REPORT_TIME) set end_time
//        //2017-04-08 update
//        if(flag==1){
//            if (!checkBatch) {
//                //every day just save the newest batch log
//                String deletebatchSql =
//                        "DELETE IQM_TRACE.DISCREPANCY_REPORT_TIME WHERE BATCH=" + this.currentBatch + " AND END_TIME IS NULL";
//                this.DiscrepancyExecute(deletebatchSql);
//                String insertbatchSql =
//                        "INSERT INTO IQM_TRACE.DISCREPANCY_REPORT_TIME(ID,BATCH,START_TIME) VALUES(IQM_TRACE.DISCREPANCY_REPORT_TIME_SEQ.nextval," +
//                                this.currentBatch + ",SYSDATE)";
//                this.DiscrepancyExecute(insertbatchSql);
//                for (int i = 0; i < exchArray.length; i++) {
//                    String exchName = exchArray[i];
//                    this.insertExchangeNavLog("INFO","Exchange:["+exchName+"] GET DATA BEGIN","EXCHANGE",exchName);
//                    System.out.println("exchange " + exchName + " begin:" + System.currentTimeMillis());
//                    Object obj = this.scratch(exchName);
//                    if(obj!=null){
//                        //System.out.println("get return object from server:"+obj);
//
//                        JSONObject jo = new JSONObject(obj.toString());
//                        String errCode = jo.get("errCode").toString();
//                        if (!errCode.equals("0")) {
//                            System.out.println("exchange " + exchName + "error occured " + errCode);
//                        }
//                        JSONArray json = JSONArray.fromObject(jo.getString("result"));
//                        List<DataBean> NDAList = (List<DataBean>) JSONArray.toList(json, DataBean.class);
//                        int ndaSize = NDAList.size();
//                        System.out.println("exchange " + exchName + " size:" + ndaSize);
//
//
//                        for (int j = 0; j < ndaSize; j++) {
//                            DataBean bean = NDAList.get(j);
//                            String sql =
//                                    "INSERT INTO IQM_TRACE.DISCREPANCY_DATA(ID,ENTITY_CREATE_DATE,RIC,PERMID,admin_status,short_sell_restriction,china_connect_eligible,china_special_treatment,common_name,ticker_symbol,trading_symbol,BATCH,exchange_name,create_time) " +
//                                            "values(IQM_TRACE.DISCREPANCY_DATA_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,'" + exchName + "',sysdate)";
//                            this.insertNDAData(sql, bean, this.currentBatch);
//                            bean = null;
//                        }
//                        this.insertExchangeNavLog("INFO","Exchange:["+exchName+"] GET DATA END","EXCHANGE",exchName);
//                        System.out.println("exchange " + exchName + " end:" + System.currentTimeMillis());
//                        obj = null;
//                        jo = null;
//                        json = null;
//                        NDAList = null;
//                    }
//                }
//                this.CreateReport(this.currentBatch);
//
//            } else {
//                System.out.println("Today's Discrepancy report between IQM and NDA has been created!");
//            }
//        }
//
//    }
//
//
//}
//
