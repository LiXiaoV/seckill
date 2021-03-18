package com.xiaov.seckill.exception;

import com.xiaov.seckill.result.CodeMsg;

/**
 * @author xiaov
 * @since 2021-03-05 20:49
 */
public class GlobalException extends RuntimeException{

    private CodeMsg codeMsg;

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }
}
