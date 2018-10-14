package com.server.bean;

/**
 * 实时新闻
 *
 * @author CYX
 * @create 2018-07-15-10:04
 */
public class RealTimeNews {

    /**
     * 数据库、内存中唯一标示(长度不限定)
     */
    private String uniqueMark;
    /**
     * 是否成功返回，若成功值为true，失败值为false。
     */
    private Boolean success;
    /**
     * 错误返回码
     * <br>
     * https://fenfa.shuwen.com/docs/error_msg?spm=fenfa.0.0.1.0o6giN
     */
    private String code;
    /**
     * 返回的错误信息
     */
    private String msg;
    /**
     * 请求的唯一ID，如果您在使用中遇到难以解决的问题，可以将此ID提交给技术支持人员。
     */
    private String requestId;
    /**
     * 返回新闻的详细信息集合。
     */
    private RealTimeNewsCollection data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RealTimeNewsCollection getData() {
        return data;
    }

    public void setData(RealTimeNewsCollection data) {
        this.data = data;
    }

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

    @Override
    public String toString() {
        return "RealTimeNews{" +
                "uniqueMark='" + uniqueMark + '\'' +
                ", success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", requestId='" + requestId + '\'' +
                ", data=" + data +
                '}';
    }
}
