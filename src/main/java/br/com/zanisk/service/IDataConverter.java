package br.com.zanisk.service;

public interface IDataConverter {
    <T> T getData(String json, Class<T> tClass);
}
