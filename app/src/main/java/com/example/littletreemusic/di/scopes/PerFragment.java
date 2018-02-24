package com.example.littletreemusic.di.scopes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
