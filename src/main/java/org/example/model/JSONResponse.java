package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后端和前端交信息互时规定的格式JSON对象
 *
 * @author nuonuo
 * @create 2021-01-03 13:16
 */
@ToString
@Getter
@Setter
public class JSONResponse {
    //业务操作是否成功
    private boolean success;
    //业务操作的消息码，一般来说，出现错误时的错误码才有意义 给开发人员定位问题
    private String code;
    //业务操作的错误消息，给用户看的信息
    private String message;
    //业务数据，业务操作成功时，给前端ajax success方法使用，解析响应json数据，渲染网页内
    private Object data;
}
