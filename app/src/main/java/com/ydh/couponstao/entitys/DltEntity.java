package com.ydh.couponstao.entitys;

import java.util.List;

/**
 * Date:2023/7/4
 * Time:11:11
 * author:ydh
 */
public class DltEntity {


    /**
     * dataFrom :
     * emptyFlag : false
     * errorCode : 0
     * errorMessage : 处理成功
     * success : true
     * value : {"lastPoolDraw":{"lotteryDrawNum":"23075","lotteryDrawResult":"07 17 20 23 32 05 11","lotteryDrawTime":"2023-07-03","lotteryGameName":"超级大乐透","lotteryGameNum":"85","poolBalanceAfterdraw":"917,148,171.29","prizeLevelList":[{"awardType":0,"group":"101","lotteryCondition":"","prizeLevel":"一等奖","sort":101,"stakeAmount":"7,321,734","stakeCount":"9","totalPrizeamount":"65,895,606"},{"awardType":0,"group":"201","lotteryCondition":"","prizeLevel":"一等奖(追加)","sort":201,"stakeAmount":"5,857,387","stakeCount":"3","totalPrizeamount":"17,572,161"},{"awardType":0,"group":"301","lotteryCondition":"","prizeLevel":"二等奖","sort":301,"stakeAmount":"57,997","stakeCount":"172","totalPrizeamount":"9,975,484"},{"awardType":0,"group":"401","lotteryCondition":"","prizeLevel":"二等奖(追加)","sort":401,"stakeAmount":"46,397","stakeCount":"36","totalPrizeamount":"1,670,292"},{"awardType":0,"group":"501","lotteryCondition":"","prizeLevel":"三等奖","sort":501,"stakeAmount":"10,000","stakeCount":"388","totalPrizeamount":"3,880,000"},{"awardType":0,"group":"601","lotteryCondition":"","prizeLevel":"四等奖","sort":601,"stakeAmount":"3,000","stakeCount":"1,659","totalPrizeamount":"4,977,000"},{"awardType":0,"group":"701","lotteryCondition":"","prizeLevel":"五等奖","sort":701,"stakeAmount":"300","stakeCount":"30,829","totalPrizeamount":"9,248,700"},{"awardType":0,"group":"801","lotteryCondition":"","prizeLevel":"六等奖","sort":801,"stakeAmount":"200","stakeCount":"39,051","totalPrizeamount":"7,810,200"},{"awardType":0,"group":"901","lotteryCondition":"","prizeLevel":"七等奖","sort":901,"stakeAmount":"100","stakeCount":"59,995","totalPrizeamount":"5,999,500"},{"awardType":0,"group":"1001","lotteryCondition":"","prizeLevel":"八等奖","sort":1001,"stakeAmount":"15","stakeCount":"1,014,657","totalPrizeamount":"15,219,855"},{"awardType":0,"group":"1101","lotteryCondition":"","prizeLevel":"九等奖","sort":1101,"stakeAmount":"5","stakeCount":"8,590,833","totalPrizeamount":"42,954,165"}]},"list":[{"drawFlowFund":"0","drawFlowFundRj":"","drawPdfUrl":"https://pdf.sporttery.cn/33800/23075/23075.pdf","estimateDrawTime":"","isGetKjpdf":1,"isGetXlpdf":2,"lotteryDrawNum":"23075","lotteryDrawResult":"07 17 20 23 32 05 11","lotteryDrawStatus":20,"lotteryDrawStatusNo":"","lotteryDrawTime":"2023-07-03","lotteryEquipmentCount":3,"lotteryGameName":"超级大乐透","lotteryGameNum":"85","lotteryGamePronum":0,"lotteryNotice":1,"lotteryNoticeShowFlag":1,"lotteryPaidBeginTime":"2023-07-03 23:30:01","lotteryPaidEndTime":"2023-09-01 23:59:59","lotteryPromotionFlag":0,"lotteryPromotionFlagRj":0,"lotterySaleBeginTime":"2023-07-01 21:10:00","lotterySaleEndTimeUnix":0,"lotterySaleEndtime":"2023-07-03 21:00:00","lotterySuspendedFlag":0,"lotteryUnsortDrawresult":"23 07 17 32 20 05 11","matchList":[],"pdfType":1,"poolBalanceAfterdraw":"917,148,171.29","poolBalanceAfterdrawRj":"","prizeLevelList":[{"awardType":0,"group":"101","lotteryCondition":"","prizeLevel":"一等奖","sort":101,"stakeAmount":"7,321,734","stakeCount":"9","totalPrizeamount":"65,895,606"},{"awardType":0,"group":"201","lotteryCondition":"","prizeLevel":"一等奖(追加)","sort":201,"stakeAmount":"5,857,387","stakeCount":"3","totalPrizeamount":"17,572,161"},{"awardType":0,"group":"301","lotteryCondition":"","prizeLevel":"二等奖","sort":301,"stakeAmount":"57,997","stakeCount":"172","totalPrizeamount":"9,975,484"},{"awardType":0,"group":"401","lotteryCondition":"","prizeLevel":"二等奖(追加)","sort":401,"stakeAmount":"46,397","stakeCount":"36","totalPrizeamount":"1,670,292"},{"awardType":0,"group":"501","lotteryCondition":"","prizeLevel":"三等奖","sort":501,"stakeAmount":"10,000","stakeCount":"388","totalPrizeamount":"3,880,000"},{"awardType":0,"group":"601","lotteryCondition":"","prizeLevel":"四等奖","sort":601,"stakeAmount":"3,000","stakeCount":"1,659","totalPrizeamount":"4,977,000"},{"awardType":0,"group":"701","lotteryCondition":"","prizeLevel":"五等奖","sort":701,"stakeAmount":"300","stakeCount":"30,829","totalPrizeamount":"9,248,700"},{"awardType":0,"group":"801","lotteryCondition":"","prizeLevel":"六等奖","sort":801,"stakeAmount":"200","stakeCount":"39,051","totalPrizeamount":"7,810,200"},{"awardType":0,"group":"901","lotteryCondition":"","prizeLevel":"七等奖","sort":901,"stakeAmount":"100","stakeCount":"59,995","totalPrizeamount":"5,999,500"},{"awardType":0,"group":"1001","lotteryCondition":"","prizeLevel":"八等奖","sort":1001,"stakeAmount":"15","stakeCount":"1,014,657","totalPrizeamount":"15,219,855"},{"awardType":0,"group":"1101","lotteryCondition":"","prizeLevel":"九等奖","sort":1101,"stakeAmount":"5","stakeCount":"8,590,833","totalPrizeamount":"42,954,165"}],"prizeLevelListRj":[],"ruleType":0,"surplusAmount":"","surplusAmountRj":"","termList":[],"termResultList":[],"totalSaleAmount":"291,887,672","totalSaleAmountRj":"","verify":1,"vtoolsConfig":{}}],"pageNo":1,"pageSize":1,"pages":2441,"total":2441}
     */

    private String dataFrom;
    private boolean emptyFlag;
    private String errorCode;
    private String errorMessage;
    private ValueBean value;

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }

    public boolean isEmptyFlag() {
        return emptyFlag;
    }

    public void setEmptyFlag(boolean emptyFlag) {
        this.emptyFlag = emptyFlag;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public static class ValueBean{
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }
    }
    public static class ListBean{
        private String lotteryDrawResult;
        private String lotteryDrawNum;

        public String getLotteryDrawResult() {
            return lotteryDrawResult;
        }

        public void setLotteryDrawResult(String lotteryDrawResult) {
            this.lotteryDrawResult = lotteryDrawResult;
        }

        public String getLotteryDrawNum() {
            return lotteryDrawNum;
        }

        public void setLotteryDrawNum(String lotteryDrawNum) {
            this.lotteryDrawNum = lotteryDrawNum;
        }
    }
}
