package com.coder.neighborhood.mvp.model;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 数据处理层
 * <pre>
 *     1. 网络的请求
 *     2. 数据的持久化
 *     3. 数据库操作
 *     ...
 *
 * </pre>
 *
 *
 * @author feng
 * @Date 2017/12/21.
 *
 */

public interface IModel {

    void onAttach(@NonNull Context context);
}