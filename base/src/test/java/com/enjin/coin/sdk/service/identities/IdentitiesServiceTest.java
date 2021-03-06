package com.enjin.coin.sdk.service.identities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.enjin.coin.sdk.BaseTestHelper;
import com.enjin.coin.sdk.config.Config;
import com.enjin.coin.sdk.service.identities.impl.IdentitiesServiceImpl;
import com.enjin.coin.sdk.util.JsonRpcUtils;
import com.enjin.coin.sdk.vo.identity.CreateIdentityRequestVO;
import com.enjin.coin.sdk.vo.identity.CreateIdentityResponseVO;
import com.enjin.coin.sdk.vo.identity.DeleteIdentityRequestVO;
import com.enjin.coin.sdk.vo.identity.DeleteIdentityResponseVO;
import com.enjin.coin.sdk.vo.identity.GetIdentityRequestVO;
import com.enjin.coin.sdk.vo.identity.GetIdentityResponseVO;
import com.enjin.coin.sdk.vo.identity.ImmutableCreateIdentityRequestVO;
import com.enjin.coin.sdk.vo.identity.ImmutableCreateIdentityResponseVO;
import com.enjin.coin.sdk.vo.identity.ImmutableDeleteIdentityRequestVO;
import com.enjin.coin.sdk.vo.identity.ImmutableGetIdentityRequestVO;
import com.enjin.coin.sdk.vo.identity.ImmutableGetIdentityResponseVO;
import com.enjin.coin.sdk.vo.identity.ImmutableUpdateIdentityRequestVO;
import com.enjin.coin.sdk.vo.identity.ImmutableUpdateIdentityResponseVO;
import com.enjin.coin.sdk.vo.identity.UpdateIdentityRequestVO;
import com.enjin.coin.sdk.vo.identity.UpdateIdentityResponseVO;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IdentitiesServiceImpl.class)
public class IdentitiesServiceTest {

	IdentitiesServiceImpl identitiesService;
	Config enjinConfig;

    @Before
	public void setUp() {
        enjinConfig = BaseTestHelper.getEnjinConfig();
	}

	@Test
	public void testContructor() {
		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		assertThat(identitiesService).isNotNull().satisfies(o -> assertThat(o.toString()).isNotEmpty());
	}

	@Test
	public void testGetIdentity_GetIdentityRequestVOIsNull() {
		GetIdentityRequestVO getIdentityRequestVO = null;

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}

	@Test
	public void testGetIdentity_AuthIsNull() {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth((String) null)
				.build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}

	@Test
	public void testGetIdentity_AuthIsEmpty() {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("").build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testGetIdentity_IdentityMapIsNull() {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap((Map) null).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}

	@Test
	public void testGetIdentity_IdentityMapIsEmpty() {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<>()).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}
	@SuppressWarnings("serial")
	@Test
	public void testGetIdentity_AfterIdentityIdIsEmpty() {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).setAfterIdentityId("")
				.build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}
	@SuppressWarnings("serial")
	@Test
	public void testGetIdentity_AfterIdentityIdIsNull() {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).setAfterIdentityId((String)null)
				.build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}
	@SuppressWarnings("serial")
	@Test
	public void testGetIdentity_LimitIsEmpty() {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).setAfterIdentityId("12345")
				.setLimit("")
				.build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}
	@SuppressWarnings("serial")
	@Test
	public void testGetIdentity_LimitIsNull() {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).setAfterIdentityId("12345")
				.setLimit((String) null)
				.build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();
	}
	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testGetIdentity_ResponseIsNull() throws Exception {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				})
				.setAfterIdentityId("1234567")
				.setLinked(true)
				.setLimit("50")
				.build();

		GetIdentityResponseVO returnedGetIdentityResponseVO = null;

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(returnedGetIdentityResponseVO);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNull();

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testGetIdentity_Success() throws Exception {
		GetIdentityRequestVO getIdentityRequestVO = ImmutableGetIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				})
				.setAfterIdentityId("1234567")
                .setLinked(true)
                .setLimit("50").build();

		GetIdentityResponseVO returnedGetIdentityResponseVO = ImmutableGetIdentityResponseVO.builder().build();
		GetIdentityResponseVO[] returnedGetIdentityResponseArray = new GetIdentityResponseVO[] {returnedGetIdentityResponseVO};

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(returnedGetIdentityResponseArray);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		GetIdentityResponseVO[] getIdentityResponseVO = identitiesService.getIdentity(getIdentityRequestVO);
		assertThat(getIdentityResponseVO).isNotNull();

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

	@Test
	public void testCreateIdentity_CreateIdentityRequestVOIsNull() {
		CreateIdentityRequestVO createIdentityRequestVO = null;

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		CreateIdentityResponseVO createIdentityResponseVO = identitiesService.createIdentity(createIdentityRequestVO);
		assertThat(createIdentityResponseVO).isNull();
	}

	@Test
	public void testCreateIdentity_AuthIsNull() {
		CreateIdentityRequestVO createIdentityRequestVO = ImmutableCreateIdentityRequestVO.builder()
				.setAuth((String) null).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		CreateIdentityResponseVO createIdentityResponseVO = identitiesService.createIdentity(createIdentityRequestVO);
		assertThat(createIdentityResponseVO).isNull();
	}

	@Test
	public void testCreateIdentity_AuthIsEmpty() {
		CreateIdentityRequestVO createIdentityRequestVO = ImmutableCreateIdentityRequestVO.builder().setAuth("")
				.build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		CreateIdentityResponseVO createIdentityResponseVO = identitiesService.createIdentity(createIdentityRequestVO);
		assertThat(createIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCreateIdentity_IdentityMapIsNull() {
		CreateIdentityRequestVO createIdentityRequestVO = ImmutableCreateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap((Map) null).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		CreateIdentityResponseVO createIdentityResponseVO = identitiesService.createIdentity(createIdentityRequestVO);
		assertThat(createIdentityResponseVO).isNull();
	}

	@Test
	public void testCreateIdentity_IdentityMapIsEmpty() {
		CreateIdentityRequestVO createIdentityRequestVO = ImmutableCreateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<>()).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		CreateIdentityResponseVO createIdentityResponseVO = identitiesService.createIdentity(createIdentityRequestVO);
		assertThat(createIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testCreateIdentity_ResponseIsNull() throws Exception {
		CreateIdentityRequestVO createIdentityRequestVO = ImmutableCreateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).build();

		CreateIdentityResponseVO returnedCreateIdentityResponseVO = null;

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(returnedCreateIdentityResponseVO);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		CreateIdentityResponseVO createIdentityResponseVO = identitiesService.createIdentity(createIdentityRequestVO);
		assertThat(createIdentityResponseVO).isNull();

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testCreateIdentity_Success() throws Exception {
		CreateIdentityRequestVO createIdentityRequestVO = ImmutableCreateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).build();

		CreateIdentityResponseVO returnedCreateIdentityResponseVO = ImmutableCreateIdentityResponseVO.builder().build();

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(returnedCreateIdentityResponseVO);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		CreateIdentityResponseVO createIdentityResponseVO = identitiesService.createIdentity(createIdentityRequestVO);
		assertThat(createIdentityResponseVO).isNotNull();

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

	@Test
	public void testUpdateIdentity_UpdateIdentityRequestVOIsNull() {
		UpdateIdentityRequestVO updateIdentityRequestVO = null;

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNull();
	}

	@Test
	public void testUpdateIdentity_AuthIsNull() {
		UpdateIdentityRequestVO updateIdentityRequestVO = ImmutableUpdateIdentityRequestVO.builder()
				.setAuth((String) null).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNull();
	}

	@Test
	public void testUpdateIdentity_AuthIsEmpty() {
		UpdateIdentityRequestVO updateIdentityRequestVO = ImmutableUpdateIdentityRequestVO.builder().setAuth("")
				.build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testUpdateIdentity_IdentityMapIsNull() {
		UpdateIdentityRequestVO updateIdentityRequestVO = ImmutableUpdateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap((Map) null).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNull();
	}

	@Test
	public void testUpdateIdentity_IdentityMapIsEmpty() {
		UpdateIdentityRequestVO updateIdentityRequestVO = ImmutableUpdateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<>()).build();
		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	@Test
	public void testUpdateIdentity_UpdateMapIsNull() {
		UpdateIdentityRequestVO updateIdentityRequestVO = ImmutableUpdateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).setUpdateMap((Map) null).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "serial" })
	@Test
	public void testUpdateIdentity_UpdateMapIsEmpty() {
		UpdateIdentityRequestVO updateIdentityRequestVO = ImmutableUpdateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).setUpdateMap(new HashMap<>()).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testUpdateIdentity_ResponseIsNull() throws Exception {
		UpdateIdentityRequestVO updateIdentityRequestVO = ImmutableUpdateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).setUpdateMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).build();

		UpdateIdentityResponseVO returnedUpdateIdentityResponseVO = null;

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(returnedUpdateIdentityResponseVO);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNull();

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testUpdateIdentity_Success() throws Exception {
		UpdateIdentityRequestVO updateIdentityRequestVO = ImmutableUpdateIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).setUpdateMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).build();

		UpdateIdentityResponseVO returnedUpdateIdentityResponseVO = ImmutableUpdateIdentityResponseVO.builder().build();

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(returnedUpdateIdentityResponseVO);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		UpdateIdentityResponseVO updateIdentityResponseVO = identitiesService.updateIdentity(updateIdentityRequestVO);
		assertThat(updateIdentityResponseVO).isNotNull();

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

	@Test
	public void testDeleteIdentity_DeleteIdentityRequestVOIsNull() {
		DeleteIdentityRequestVO deleteIdentityRequestVO = null;

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		DeleteIdentityResponseVO deleteIdentityResponseVO = identitiesService.deleteIdentity(deleteIdentityRequestVO);
		assertThat(deleteIdentityResponseVO).isNull();
	}

	@Test
	public void testDeleteIdentity_AuthIsNull() {
		DeleteIdentityRequestVO deleteIdentityRequestVO = ImmutableDeleteIdentityRequestVO.builder()
				.setAuth((String) null).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		DeleteIdentityResponseVO deleteIdentityResponseVO = identitiesService.deleteIdentity(deleteIdentityRequestVO);
		assertThat(deleteIdentityResponseVO).isNull();
	}

	@Test
	public void testDeleteIdentity_AuthIsEmpty() {
		DeleteIdentityRequestVO deleteIdentityRequestVO = ImmutableDeleteIdentityRequestVO.builder().setAuth("")
				.build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		DeleteIdentityResponseVO deleteIdentityResponseVO = identitiesService.deleteIdentity(deleteIdentityRequestVO);
		assertThat(deleteIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testDeleteIdentity_IdentityMapIsNull() {
		DeleteIdentityRequestVO deleteIdentityRequestVO = ImmutableDeleteIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap((Map) null).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		DeleteIdentityResponseVO deleteIdentityResponseVO = identitiesService.deleteIdentity(deleteIdentityRequestVO);
		assertThat(deleteIdentityResponseVO).isNull();
	}

	@Test
	public void testDeleteIdentity_IdentityMapIsEmpty() {
		DeleteIdentityRequestVO deleteIdentityRequestVO = ImmutableDeleteIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<>()).build();

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		DeleteIdentityResponseVO deleteIdentityResponseVO = identitiesService.deleteIdentity(deleteIdentityRequestVO);
		assertThat(deleteIdentityResponseVO).isNull();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testDeleteIdentity_ResultIsNull() throws Exception {
		DeleteIdentityRequestVO deleteIdentityRequestVO = ImmutableDeleteIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).build();

		Boolean result = null;

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(result);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		DeleteIdentityResponseVO deleteIdentityResponseVO = identitiesService.deleteIdentity(deleteIdentityRequestVO);
		assertThat(deleteIdentityResponseVO).isNotNull().satisfies(o -> assertThat(o.getResult()).isNotPresent());

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testDeleteIdentity_ResultIsFalse() throws Exception {
		DeleteIdentityRequestVO deleteIdentityRequestVO = ImmutableDeleteIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).build();

		Boolean result = false;

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(result);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		DeleteIdentityResponseVO deleteIdentityResponseVO = identitiesService.deleteIdentity(deleteIdentityRequestVO);
		assertThat(deleteIdentityResponseVO).isNotNull()
				.satisfies(o -> assertThat(o.getResult()).isPresent().hasValueSatisfying(v -> assertThat(v).isFalse()));

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testDeleteIdentity_Success() throws Exception {
		DeleteIdentityRequestVO deleteIdentityRequestVO = ImmutableDeleteIdentityRequestVO.builder().setAuth("auth")
				.setIdentityMap(new HashMap<String, Object>() {
					{
						put("key", "value");
					}
				}).build();

		Boolean result = true;

		JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
		PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
		Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.isA(Map.class))).thenReturn(result);

		identitiesService = new IdentitiesServiceImpl(enjinConfig);
		DeleteIdentityResponseVO deleteIdentityResponseVO = identitiesService.deleteIdentity(deleteIdentityRequestVO);
		assertThat(deleteIdentityResponseVO).isNotNull()
				.satisfies(o -> assertThat(o.getResult()).isPresent().hasValueSatisfying(v -> assertThat(v).isTrue()));

		PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
		Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(),
				Mockito.anyString(), Mockito.isA(Map.class));
	}

}
