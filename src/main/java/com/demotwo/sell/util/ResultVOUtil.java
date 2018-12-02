package com.demotwo.sell.util;

import com.demotwo.sell.vo.ResultVO;

import java.io.Serializable;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 23:02
 * @Desc:
 */
public class ResultVOUtil  {

    public static ResultVO success(Object o) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(o);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("失败");
        return resultVO;
    }

    public static ResultVO error(Integer code, String codeInfo) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(codeInfo);
        return resultVO;
    }
}