package com.quick.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 错误码
 * @author vector
 *
 */
public enum ResultStatus {

	// -1为通用失败（根据ApiResult.java中的构造方法注释而来）
	FAIL(-1, "common fail"),
	// 0为成功
	SUCCESS(0, "success"),

	error_pic_file(3,"非法图片文件"),
	error_pic_upload(4,"图片上传失败"),
	error_record_not_found(5, "没有找到对应的数据"),
	error_max_page_size(6, "请求记录数超出每次请求最大允许值"),
	error_create_failed(7,"新增失败"),
	error_update_failed(8,"修改失败"),
	error_delete_failed(9,"删除失败"),
	error_search_failed(10,"查询失败"),
	error_count_failed(11,"查询数据总数失败"),
	error_string_to_obj(12,"字符串转java对象失败"),
	error_invalid_argument(13,"参数不合法"),
	error_update_not_allowed(14,"更新失败：%s"),
	error_duplicated_data(15,"数据已存在"),
	error_unknown_database_operation(16,"未知数据库操作失败，请联系管理员解决"),
	error_column_unique(17,"字段s%违反唯一约束性条件"),
	error_file_download(18,"文件下载失败"),
	error_file_upload(19,"文件上传失败"),

	//100-511为http 状态码
	// --- 4xx Client Error ---
	http_status_bad_request(400, "Bad Request"),
	http_status_unauthorized(401, "Unauthorized"),
	http_status_payment_required(402, "Payment Required"),
	http_status_forbidden(403, "Forbidden"),
	http_status_not_found(404, "Not Found"),
	http_status_method_not_allowed(405, "Method Not Allowed"),
	http_status_not_acceptable(406, "Not Acceptable"),
	http_status_proxy_authentication_required(407, "Proxy Authentication Required"),
	http_status_request_timeout(408, "Request Timeout"),
	http_status_conflict(409, "Conflict"),
	http_status_gone(410, "Gone"),
	http_status_length_required(411, "Length Required"),
	http_status_precondition_failed(412, "Precondition Failed"),
	http_status_payload_too_large(413, "Payload Too Large"),
	http_status_uri_too_long(414, "URI Too Long"),
	http_status_unsupported_media_type(415, "Unsupported Media Type"),
	http_status_requested_range_not_satisfiable(416, "Requested range not satisfiable"),
	http_status_expectation_failed(417, "Expectation Failed"),
	http_status_im_a_teapot(418, "I'm a teapot"),
	http_status_unprocessable_entity(422, "Unprocessable Entity"),
	http_status_locked(423, "Locked"),
	http_status_failed_dependency(424, "Failed Dependency"),
	http_status_upgrade_required(426, "Upgrade Required"),
	http_status_precondition_required(428, "Precondition Required"),
	http_status_too_many_requests(429, "Too Many Requests"),
	http_status_request_header_fields_too_large(431, "Request Header Fields Too Large"),

	// --- 5xx Server Error ---
	http_status_internal_server_error(500, "系统错误"),
	http_status_not_implemented(501, "Not Implemented"),
	http_status_bad_gateway(502, "Bad Gateway"),
	http_status_service_unavailable(503, "Service Unavailable"),
	http_status_gateway_timeout(504, "Gateway Timeout"),
	http_status_http_version_not_supported(505, "HTTP Version not supported"),
	http_status_variant_also_negotiates(506, "Variant Also Negotiates"),
	http_status_insufficient_storage(507, "Insufficient Storage"),
	http_status_loop_detected(508, "Loop Detected"),
	http_status_bandwidth_limit_exceeded(509, "Bandwidth Limit Exceeded"),
	http_status_not_extended(510, "Not Extended"),
	http_status_network_authentication_required(511, "Network Authentication Required"),

	// --- 8xx common error ---
	EXCEPTION(800, "exception"),
	INVALID_PARAM(801, "invalid.param"),
	INVALID_PRIVI(802, "invalid.privi"),

	//1000以内是系统错误，
	no_login(1000,"没有登录"),
	config_error(1001,"参数配置表错误"),
	user_exist(1002,"用户名已存在"),
	userpwd_not_exist(1003,"用户名不存在或者密码错误"),




	;
	private static final Logger LOGGER = LoggerFactory.getLogger(ResultStatus.class);


	private int code;
	private String msg;

	ResultStatus(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public static int getCode(String define){
		try {
			return ResultStatus.valueOf(define).code;
		} catch (IllegalArgumentException e) {
			LOGGER.error("undefined error code: {}", define);
			return FAIL.getErrorCode();
		}
	}

	public static String getMsg(String define){
		try {
			return ResultStatus.valueOf(define).msg;
		} catch (IllegalArgumentException e) {
			LOGGER.error("undefined error code: {}", define);
			return FAIL.getErrorMsg();
		}

	}

	public static String getMsg(int code){
		for(ResultStatus err : ResultStatus.values()){
			if(err.code==code){
				return err.msg;
			}
		}
		return "errorCode not defined ";
	}

	public int getErrorCode(){
		return code;
	}

	public String getErrorMsg(){
		return msg;
	}

}