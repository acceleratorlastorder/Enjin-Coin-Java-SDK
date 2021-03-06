package com.enjin.coin.sdk.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.enjin.coin.sdk.vo.event.GetEventResponseVO;
import com.enjin.coin.sdk.vo.event.ImmutableGetEventResponseVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonUtils.class, Gson.class, GsonBuilder.class, FileInputStream.class, InputStreamReader.class})
public class JsonUtilsTest {

    @Test
    public void testConstructor() {
        JsonUtils jsonUtils = new JsonUtils();
        assertThat(jsonUtils).isNotNull();
    }

    @Test
    public void testConvertJsonToObject_JsonStringIsEmpty() {
        String jsonString = "";
        Class<?> responseClass = GetEventResponseVO.class;
        Gson mockGson = PowerMockito.mock(Gson.class);
        Object responseObject = JsonUtils.convertJsonToObject(mockGson, jsonString, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonToObject_JsonStringIsNull() {
        Gson mockGson = PowerMockito.mock(Gson.class);
        String jsonString = null;
        Class<?> responseClass = GetEventResponseVO.class;
        Object responseObject = JsonUtils.convertJsonToObject(mockGson, jsonString, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonToObject_ResponseObjectIsNull() {
        String jsonString = "{}";
        Class<?> responseClass = null;
        Gson mockGson = PowerMockito.mock(Gson.class);
        Object responseObject = JsonUtils.convertJsonToObject(mockGson, jsonString, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonToObject_GsonIsNull() throws Exception {
        String jsonString = "{}";
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = null;

        Object responseObject = JsonUtils.convertJsonToObject(mockGson, jsonString, responseClass);
        assertThat(responseObject).isNull();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testConvertJsonToObject_JsonSyntaxException() throws Exception {
        String jsonString = "{}";
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = PowerMockito.mock(Gson.class);

        Mockito.when(mockGson.fromJson(Mockito.anyString(), Mockito.isA(Class.class))).thenThrow(new JsonSyntaxException("exception"));
        Object responseObject = JsonUtils.convertJsonToObject(mockGson, jsonString, responseClass);
        assertThat(responseObject).isNull();

        Mockito.verify(mockGson, Mockito.times(1)).fromJson(Mockito.anyString(), Mockito.isA(Class.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testConvertJsonToObject_Success() throws Exception {
        String jsonString = "{}";
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = PowerMockito.mock(Gson.class);

        Mockito.when(mockGson.fromJson(Mockito.anyString(), Mockito.isA(Class.class))).thenReturn(ImmutableGetEventResponseVO.builder().build());
        Object responseObject = JsonUtils.convertJsonToObject(mockGson, jsonString, responseClass);
        assertThat(responseObject).isNotNull();

        Mockito.verify(mockGson, Mockito.times(1)).fromJson(Mockito.anyString(), Mockito.isA(Class.class));
    }

    @Test
    public void testConvertJsonFromFileToObject_FilePathIsEmpty() {
        String filePath = "";
        Class<?> responseClass = GetEventResponseVO.class;
        Gson mockGson = PowerMockito.mock(Gson.class);

        Object responseObject = JsonUtils.convertJsonFromFileToObject(mockGson, filePath, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonFromFileToObject_FilePathIsNull() {
        String filePath = null;
        Class<?> responseClass = GetEventResponseVO.class;
        Gson mockGson = PowerMockito.mock(Gson.class);

        Object responseObject = JsonUtils.convertJsonFromFileToObject(mockGson, filePath, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonFromFileToObject_ResponseObjectIsNull() {
        String filePath = "{}";
        Class<?> responseClass = null;
        Gson mockGson = PowerMockito.mock(Gson.class);

        Object responseObject = JsonUtils.convertJsonFromFileToObject(mockGson, filePath, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonFromFileToObject_GsonIsNull() throws Exception {
        String filePath = "{}";
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = null;

        Object responseObject = JsonUtils.convertJsonFromFileToObject(mockGson, filePath, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonFromFileToObject_FileNotFoundException() throws Exception {
        String filePath = "{}";
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = PowerMockito.mock(Gson.class);
        PowerMockito.whenNew(FileInputStream.class).withArguments(Mockito.anyString()).thenThrow(new FileNotFoundException());
        Object responseObject = JsonUtils.convertJsonFromFileToObject(mockGson, filePath, responseClass);
        assertThat(responseObject).isNull();

        PowerMockito.verifyNew(FileInputStream.class, Mockito.times(1)).withArguments(Mockito.anyString());
    }

    @Test
    public void testConvertJsonFromFileToObject_UnsupportedEncodingException() throws Exception {
        String filePath = "{}";
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = PowerMockito.mock(Gson.class);
        FileInputStream mockFileInputStream = Mockito.mock(FileInputStream.class);

        PowerMockito.whenNew(FileInputStream.class).withArguments(Mockito.anyString()).thenReturn(mockFileInputStream);
        PowerMockito.whenNew(InputStreamReader.class).withArguments(Mockito.isA(InputStream.class), Mockito.anyString()).thenThrow(new UnsupportedEncodingException());
        Object responseObject = JsonUtils.convertJsonFromFileToObject(mockGson, filePath, responseClass);
        assertThat(responseObject).isNull();

        PowerMockito.verifyNew(FileInputStream.class, Mockito.times(1)).withArguments(Mockito.anyString());
        PowerMockito.verifyNew(InputStreamReader.class, Mockito.times(1)).withArguments(Mockito.isA(InputStream.class), Mockito.anyString());
    }

    @Test
    public void testConvertJsonFromFileToObject_Success() throws Exception {
        String filePath = "{}";
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = PowerMockito.mock(Gson.class);
        FileInputStream mockFileInputStream = Mockito.mock(FileInputStream.class);
        InputStreamReader mockInputStreamReader = Mockito.mock(InputStreamReader.class);
        JsonReader mockJsonReader = Mockito.mock(JsonReader.class);

        PowerMockito.whenNew(FileInputStream.class).withArguments(Mockito.anyString()).thenReturn(mockFileInputStream);
        PowerMockito.whenNew(InputStreamReader.class).withArguments(Mockito.isA(InputStream.class), Mockito.anyString()).thenReturn(mockInputStreamReader);
        PowerMockito.whenNew(JsonReader.class).withArguments(Mockito.isA(Reader.class)).thenReturn(mockJsonReader);
        Mockito.when(mockGson.fromJson(Mockito.isA(JsonReader.class), Mockito.isA(Class.class))).thenReturn(ImmutableGetEventResponseVO.builder().build());
        Object responseObject = JsonUtils.convertJsonFromFileToObject(mockGson, filePath, responseClass);
        assertThat(responseObject).isNotNull();

        PowerMockito.verifyNew(FileInputStream.class, Mockito.times(1)).withArguments(Mockito.anyString());
        PowerMockito.verifyNew(InputStreamReader.class, Mockito.times(1)).withArguments(Mockito.isA(InputStream.class), Mockito.anyString());
        PowerMockito.verifyNew(JsonReader.class, Mockito.times(1)).withArguments(Mockito.isA(Reader.class));
        Mockito.verify(mockGson, Mockito.times(1)).fromJson(Mockito.isA(JsonReader.class), Mockito.isA(Class.class));
    }

    @Test
    public void testConvertJsonFromFileReaderToObject_FileReaderIsNull() {
        FileReader mockFileReader = null;
        Class<?> responseClass = GetEventResponseVO.class;
        Gson mockGson = PowerMockito.mock(Gson.class);

        Object responseObject = JsonUtils.convertJsonFromFileReaderToObject(mockGson, mockFileReader, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonFromFileReaderToObject_ResponseObjectIsNull() {
        FileReader mockFileReader = Mockito.mock(FileReader.class);
        Class<?> responseClass = null;
        Gson mockGson = PowerMockito.mock(Gson.class);

        Object responseObject = JsonUtils.convertJsonFromFileReaderToObject(mockGson, mockFileReader, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonFromFileReaderToObject_GsonIsNull() throws Exception {
        FileReader mockFileReader = Mockito.mock(FileReader.class);
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = null;

        Object responseObject = JsonUtils.convertJsonFromFileReaderToObject(mockGson, mockFileReader, responseClass);
        assertThat(responseObject).isNull();
    }

    @Test
    public void testConvertJsonFromFileReaderToObject_JsonSyntaxException() throws Exception {
        FileReader mockFileReader = Mockito.mock(FileReader.class);
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = PowerMockito.mock(Gson.class);
        JsonReader mockJsonReader = Mockito.mock(JsonReader.class);

        PowerMockito.whenNew(JsonReader.class).withArguments(Mockito.isA(FileReader.class)).thenReturn(mockJsonReader);
        Mockito.when(mockGson.fromJson(Mockito.isA(JsonReader.class), Mockito.isA(Class.class))).thenThrow(new JsonSyntaxException("exception"));
        Object responseObject = JsonUtils.convertJsonFromFileReaderToObject(mockGson, mockFileReader, responseClass);
        assertThat(responseObject).isNull();

        PowerMockito.verifyNew(JsonReader.class, Mockito.times(1)).withArguments(Mockito.isA(FileReader.class));
        Mockito.verify(mockGson, Mockito.times(1)).fromJson(Mockito.isA(JsonReader.class), Mockito.isA(Class.class));
    }

    @Test
    public void testConvertJsonFromFileReaderToObject_Success() throws Exception {
        FileReader mockFileReader = Mockito.mock(FileReader.class);
        Class<?> responseClass = GetEventResponseVO.class;

        Gson mockGson = PowerMockito.mock(Gson.class);
        JsonReader mockJsonReader = Mockito.mock(JsonReader.class);

        PowerMockito.whenNew(JsonReader.class).withArguments(Mockito.isA(FileReader.class)).thenReturn(mockJsonReader);
        Mockito.when(mockGson.fromJson(Mockito.isA(JsonReader.class), Mockito.isA(Class.class))).thenReturn(ImmutableGetEventResponseVO.builder().build());
        Object responseObject = JsonUtils.convertJsonFromFileReaderToObject(mockGson, mockFileReader, responseClass);
        assertThat(responseObject).isNotNull();

        PowerMockito.verifyNew(JsonReader.class, Mockito.times(1)).withArguments(Mockito.isA(FileReader.class));
        Mockito.verify(mockGson, Mockito.times(1)).fromJson(Mockito.isA(JsonReader.class), Mockito.isA(Class.class));
    }

    @Test
    public void testConvertObjectToJson_JsonObjectIsNull() {
        Object jsonObject = null;
        Gson mockGson = PowerMockito.mock(Gson.class);

        String jsonResponse = JsonUtils.convertObjectToJson(mockGson, jsonObject);
        assertThat(jsonResponse).isNull();
    }

    @Test
    public void testConvertObjectToJson_GonObjectIsNull() {
        GetEventResponseVO jsonObject = ImmutableGetEventResponseVO.builder().build();
        Gson mockGson = null;
        String jsonResponse = JsonUtils.convertObjectToJson(mockGson, jsonObject);
        assertThat(jsonResponse).isNull();
    }

    @Test
    public void testConvertObjectToJson_SuccessButJsonIsEmpty() throws Exception {
        String jsonStrResponse = "";
        GetEventResponseVO jsonObject = ImmutableGetEventResponseVO.builder().build();

        Gson mockGson = PowerMockito.mock(Gson.class);

        PowerMockito.when(mockGson.toJson(Mockito.isA(GetEventResponseVO.class))).thenReturn(jsonStrResponse);

        String jsonResponse = JsonUtils.convertObjectToJson(mockGson, jsonObject);
        assertThat(jsonResponse).isNotNull().satisfies(o -> assertThat(o.length()).isEqualTo(0));

        Mockito.verify(mockGson, Mockito.times(1)).toJson(Mockito.isA(GetEventResponseVO.class));
    }

    @Test
    public void testConvertObjectToJson_Success() throws Exception {
        String jsonStrResponse = "{\"event_id\":\"1\"}";
        GetEventResponseVO jsonObject = ImmutableGetEventResponseVO.builder().build();

        Gson mockGson = PowerMockito.mock(Gson.class);

        PowerMockito.when(mockGson.toJson(Mockito.isA(GetEventResponseVO.class))).thenReturn(jsonStrResponse);

        String jsonResponse = JsonUtils.convertObjectToJson(mockGson, jsonObject);
        assertThat(jsonResponse).isNotNull().satisfies(o -> assertThat(o.length()).isGreaterThan(0));

        Mockito.verify(mockGson, Mockito.times(1)).toJson(Mockito.isA(GetEventResponseVO.class));
    }

    @Test
    public void testConvertObjectToJsonTree_JsonObjectIsNull() {
        Object jsonObject = null;
        Gson mockGson = PowerMockito.mock(Gson.class);

        JsonElement jsonElement = JsonUtils.convertObjectToJsonTree(mockGson, jsonObject);
        assertThat(jsonElement).isNull();
    }

    @Test
    public void testConvertObjectToJsonTree_GonObjectIsNull() {
        GetEventResponseVO jsonObject = ImmutableGetEventResponseVO.builder().build();
        Gson mockGson = null;
        JsonElement jsonElement = JsonUtils.convertObjectToJsonTree(mockGson, jsonObject);
        assertThat(jsonElement).isNull();
    }

    @Test
    public void testConvertObjectToJsonTree_SuccessButJsonIsNull() throws Exception {
        JsonElement requestJsonElement = null;
        GetEventResponseVO jsonObject = ImmutableGetEventResponseVO.builder().build();

        Gson mockGson = PowerMockito.mock(Gson.class);

        PowerMockito.when(mockGson.toJsonTree(Mockito.isA(GetEventResponseVO.class))).thenReturn(requestJsonElement);

        JsonElement jsonElement = JsonUtils.convertObjectToJsonTree(mockGson, jsonObject);
        assertThat(jsonElement).isNull();

        Mockito.verify(mockGson, Mockito.times(1)).toJsonTree(Mockito.isA(GetEventResponseVO.class));
    }

    @Test
    public void testConvertObjectToJsonTree_Success() throws Exception {
        JsonElement requestJsonElement = Mockito.mock(JsonElement.class);
        GetEventResponseVO jsonObject = ImmutableGetEventResponseVO.builder().build();

        Gson mockGson = PowerMockito.mock(Gson.class);

        PowerMockito.when(mockGson.toJsonTree(Mockito.isA(GetEventResponseVO.class))).thenReturn(requestJsonElement);

        JsonElement jsonElement = JsonUtils.convertObjectToJsonTree(mockGson, jsonObject);
        assertThat(jsonElement).isNotNull();

        Mockito.verify(mockGson, Mockito.times(1)).toJsonTree(Mockito.isA(GetEventResponseVO.class));
    }
}
