package com.ydh.couponstao.entitys;

import java.util.List;

/**
 * Date:2023/7/4
 * Time:14:07
 * author:ydh
 */
public class SsqEntity {

    /**
     * state : 0
     * message : 查询成功
     * total : 30
     * pageNum : 30
     * pageNo : 1
     * pageSize : 1
     * Tflag : 0
     * result : [{"name":"双色球","code":"2023075","detailsLink":"/c/2023/07/02/545910.shtml","videoLink":"/c/2023/07/02/545908.shtml","date":"2023-07-02(日)","week":"日","red":"13,15,24,28,30,31","blue":"01","blue2":"","sales":"382962350","poolmoney":"2313625531","content":"河北1注,辽宁1注,福建2注,湖南3注,四川1注,新疆1注,共9注。","addmoney":"","addmoney2":"","msg":"","z2add":"","m2add":"","prizegrades":[{"type":1,"typenum":"9","typemoney":"6942068"},{"type":2,"typenum":"107","typemoney":"204189"},{"type":3,"typenum":"1526","typemoney":"3000"},{"type":4,"typenum":"81441","typemoney":"200"},{"type":5,"typenum":"1516836","typemoney":"10"},{"type":6,"typenum":"12844785","typemoney":"5"},{"type":7,"typenum":"","typemoney":""}]}]
     */

    private String message;

    private List<ResultBean> result;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : 双色球
         * code : 2023075
         * detailsLink : /c/2023/07/02/545910.shtml
         * videoLink : /c/2023/07/02/545908.shtml
         * date : 2023-07-02(日)
         * week : 日
         * red : 13,15,24,28,30,31
         * blue : 01
         * blue2 :
         * sales : 382962350
         * poolmoney : 2313625531
         * content : 河北1注,辽宁1注,福建2注,湖南3注,四川1注,新疆1注,共9注。
         * addmoney :
         * addmoney2 :
         * msg :
         * z2add :
         * m2add :
         * prizegrades : [{"type":1,"typenum":"9","typemoney":"6942068"},{"type":2,"typenum":"107","typemoney":"204189"},{"type":3,"typenum":"1526","typemoney":"3000"},{"type":4,"typenum":"81441","typemoney":"200"},{"type":5,"typenum":"1516836","typemoney":"10"},{"type":6,"typenum":"12844785","typemoney":"5"},{"type":7,"typenum":"","typemoney":""}]
         */

        private String code;
        private String red;
        private String blue;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }



        public String getRed() {
            return red;
        }

        public void setRed(String red) {
            this.red = red;
        }

        public String getBlue() {
            return blue;
        }

        public void setBlue(String blue) {
            this.blue = blue;
        }

    }
}
