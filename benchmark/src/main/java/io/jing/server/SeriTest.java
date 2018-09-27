package io.jing.server;
import io.jing.server.thrift.ResourceT._Fields;

import io.jing.base.bean.Rsp;
import io.jing.base.thrift.RspBean;
import io.jing.base.util.json.JsonUtil;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.thrift.ResourceT;
import io.jing.server.thrift.ResponseT;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Benchmark)
@BenchmarkMode({ Mode.Throughput, Mode.AverageTime, Mode.SampleTime })
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 1,time = 5)
@Measurement(iterations = 3,time = 5)
@Threads(32)
@Fork(1)
public class SeriTest {

    private static String str = "[{\"createdAt\":1537233516815,\"updatedAt\":1537233516815,\"deletedAt\":-1,\"id\":10001,\"name\":\"资源查询\",\"code\":\"RESOURCE_QUERY\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/resource/query\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516950,\"updatedAt\":1537233516950,\"deletedAt\":-1,\"id\":10002,\"name\":\"资源新增\",\"code\":\"RESOURCE_INSERT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/resource/insert\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516951,\"updatedAt\":1537233516951,\"deletedAt\":-1,\"id\":10003,\"name\":\"资源修改\",\"code\":\"RESOURCE_UPDATE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/resource/update\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516951,\"updatedAt\":1537233516951,\"deletedAt\":-1,\"id\":10004,\"name\":\"资源删除\",\"code\":\"RESOURCE_DELETE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/resource/delete\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516952,\"updatedAt\":1537233516952,\"deletedAt\":-1,\"id\":10005,\"name\":\"角色查询\",\"code\":\"ROLE_QUERY\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/role/query\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516953,\"updatedAt\":1537233516953,\"deletedAt\":-1,\"id\":10006,\"name\":\"角色新增\",\"code\":\"ROLE_INSERT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/role/insert\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516954,\"updatedAt\":1537233516954,\"deletedAt\":-1,\"id\":10007,\"name\":\"角色修改\",\"code\":\"ROLE_UPDATE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/role/update\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516955,\"updatedAt\":1537233516955,\"deletedAt\":-1,\"id\":10008,\"name\":\"角色删除\",\"code\":\"ROLE_DELETE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/role/delete\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516956,\"updatedAt\":1537233516956,\"deletedAt\":-1,\"id\":10009,\"name\":\"用户角色查询\",\"code\":\"USER_ROLE_QUERY\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/userRole/query\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516957,\"updatedAt\":1537233516957,\"deletedAt\":-1,\"id\":10010,\"name\":\"用户角色新增\",\"code\":\"USER_ROLE_INSERT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/userRole/insert\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516958,\"updatedAt\":1537233516958,\"deletedAt\":-1,\"id\":10011,\"name\":\"用户角色修改\",\"code\":\"USER_ROLE_UPDATE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/userRole/update\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516959,\"updatedAt\":1537233516959,\"deletedAt\":-1,\"id\":10012,\"name\":\"用户角色删除\",\"code\":\"USER_ROLE_DELETE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/acl/userRole/delete\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516971,\"updatedAt\":1537233516971,\"deletedAt\":-1,\"id\":10013,\"name\":\"管理员查询\",\"code\":\"ADMIN_QUERY\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/admin/query\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516972,\"updatedAt\":1537233516972,\"deletedAt\":-1,\"id\":10014,\"name\":\"管理员新增\",\"code\":\"ADMIN_INSERT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/admin/insert\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516973,\"updatedAt\":1537233516973,\"deletedAt\":-1,\"id\":10015,\"name\":\"管理员修改\",\"code\":\"ADMIN_UPDATE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/admin/update\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516974,\"updatedAt\":1537233516974,\"deletedAt\":-1,\"id\":10016,\"name\":\"管理员删除\",\"code\":\"ADMIN_DELETE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/admin/delete\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516976,\"updatedAt\":1537233516976,\"deletedAt\":-1,\"id\":10017,\"name\":\"用户查询\",\"code\":\"USER_QUERY\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/user/query\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516981,\"updatedAt\":1537233516981,\"deletedAt\":-1,\"id\":10018,\"name\":\"用户新增\",\"code\":\"USER_INSERT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/user/insert\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516982,\"updatedAt\":1537233516982,\"deletedAt\":-1,\"id\":10019,\"name\":\"用户修改\",\"code\":\"USER_UPDATE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/user/update\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516986,\"updatedAt\":1537233516986,\"deletedAt\":-1,\"id\":10020,\"name\":\"用户删除\",\"code\":\"USER_DELETE\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/user/delete\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516986,\"updatedAt\":1537233516986,\"deletedAt\":-1,\"id\":10021,\"name\":\"acl.myResource\",\"code\":\"ACL_MY_RESOURCE\",\"description\":\"\",\"method\":\"GET\",\"uri\":\"/acl/myResource\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516987,\"updatedAt\":1537233516987,\"deletedAt\":-1,\"id\":10022,\"name\":\"user.delUser\",\"code\":\"USER_DEL_USER\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/delUser\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516987,\"updatedAt\":1537233516987,\"deletedAt\":-1,\"id\":10023,\"name\":\"user.editUser\",\"code\":\"USER_EDIT_USER\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/editUser\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516988,\"updatedAt\":1537233516988,\"deletedAt\":-1,\"id\":10024,\"name\":\"user.getToken\",\"code\":\"USER_GET_TOKEN\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/getToken\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516988,\"updatedAt\":1537233516988,\"deletedAt\":-1,\"id\":10025,\"name\":\"user.getUser\",\"code\":\"USER_GET_USER\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/getUser\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233516989,\"updatedAt\":1537233516989,\"deletedAt\":-1,\"id\":10026,\"name\":\"user.login\",\"code\":\"USER_LOGIN\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/login\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517004,\"updatedAt\":1537233517004,\"deletedAt\":-1,\"id\":10027,\"name\":\"user.logout\",\"code\":\"USER_LOGOUT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/logout\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517009,\"updatedAt\":1537233517009,\"deletedAt\":-1,\"id\":10028,\"name\":\"user.me\",\"code\":\"USER_ME\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/me\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517009,\"updatedAt\":1537233517009,\"deletedAt\":-1,\"id\":10029,\"name\":\"user.regUser\",\"code\":\"USER_REG_USER\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/regUser\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517009,\"updatedAt\":1537233517009,\"deletedAt\":-1,\"id\":10030,\"name\":\"user.resetPwd\",\"code\":\"USER_RESET_PWD\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/resetPwd\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517009,\"updatedAt\":1537233517009,\"deletedAt\":-1,\"id\":10031,\"name\":\"user.searchUser\",\"code\":\"USER_SEARCH_USER\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/user/searchUser\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517010,\"updatedAt\":1537233517010,\"deletedAt\":-1,\"id\":10032,\"name\":\"admin.getToken\",\"code\":\"ADMIN_GET_TOKEN\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/getToken\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517010,\"updatedAt\":1537233517010,\"deletedAt\":-1,\"id\":10033,\"name\":\"admin.login\",\"code\":\"ADMIN_LOGIN\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/login\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517010,\"updatedAt\":1537233517010,\"deletedAt\":-1,\"id\":10034,\"name\":\"admin.logout\",\"code\":\"ADMIN_LOGOUT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/logout\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517010,\"updatedAt\":1537233517010,\"deletedAt\":-1,\"id\":10035,\"name\":\"admin.me\",\"code\":\"ADMIN_ME\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/me\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517010,\"updatedAt\":1537233517010,\"deletedAt\":-1,\"id\":10036,\"name\":\"admin.register\",\"code\":\"ADMIN_REGISTER\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/register\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517011,\"updatedAt\":1537233517011,\"deletedAt\":-1,\"id\":10037,\"name\":\"admin.resetPwd\",\"code\":\"ADMIN_RESET_PWD\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/admin/resetPwd\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517011,\"updatedAt\":1537233517011,\"deletedAt\":-1,\"id\":10038,\"name\":\"relationship.addContact\",\"code\":\"RELATIONSHIP_ADD_CONTACT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/addContact\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517011,\"updatedAt\":1537233517011,\"deletedAt\":-1,\"id\":10039,\"name\":\"relationship.addRoomUser\",\"code\":\"RELATIONSHIP_ADD_ROOM_USER\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/addRoomUser\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517011,\"updatedAt\":1537233517011,\"deletedAt\":-1,\"id\":10040,\"name\":\"relationship.createRoom\",\"code\":\"RELATIONSHIP_CREATE_ROOM\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/createRoom\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517011,\"updatedAt\":1537233517011,\"deletedAt\":-1,\"id\":10041,\"name\":\"relationship.delContact\",\"code\":\"RELATIONSHIP_DEL_CONTACT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/delContact\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517012,\"updatedAt\":1537233517012,\"deletedAt\":-1,\"id\":10042,\"name\":\"relationship.leaveRoom\",\"code\":\"RELATIONSHIP_LEAVE_ROOM\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/leaveRoom\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517012,\"updatedAt\":1537233517012,\"deletedAt\":-1,\"id\":10043,\"name\":\"relationship.listContact\",\"code\":\"RELATIONSHIP_LIST_CONTACT\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/listContact\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517012,\"updatedAt\":1537233517012,\"deletedAt\":-1,\"id\":10044,\"name\":\"relationship.ListRoom\",\"code\":\"RELATIONSHIP_LIST_ROOM\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/ListRoom\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517012,\"updatedAt\":1537233517012,\"deletedAt\":-1,\"id\":10045,\"name\":\"relationship.listRoomUser\",\"code\":\"RELATIONSHIP_LIST_ROOM_USER\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/listRoomUser\",\"type\":1,\"state\":1,\"logout\":false},{\"createdAt\":1537233517013,\"updatedAt\":1537233517013,\"deletedAt\":-1,\"id\":10046,\"name\":\"relationship.searchRoom\",\"code\":\"RELATIONSHIP_SEARCH_ROOM\",\"description\":\"\",\"method\":\"POST\",\"uri\":\"/relationship/searchRoom\",\"type\":1,\"state\":1,\"logout\":false}]";

    private static List<ResourceBean> rs = JsonUtil.toList(str,ResourceBean.class);
    private static ResourceBean rb = rs.get(0);
    private static TProtocolFactory factory = new TBinaryProtocol.Factory();


    @Benchmark
    public byte[] json() throws Exception{
        Rsp rsp = new Rsp();
        rsp.setCode(0);
        rsp.setMessage("success");
        rsp.setData(rs);
        RspBean rspBean = rsp.rspBean();

        TSerializer serializer = new TSerializer(factory);
        return serializer.serialize(rspBean);
    }

    @Benchmark
    public byte[] thrift() throws Exception{
        ResponseT responseT = new ResponseT();
        List<ResourceT> resources = rs.stream().map(r -> {
            ResourceT rt = new ResourceT();
            rt.setId(r.getId());
            rt.setName(r.getName());
            rt.setCode(r.getCode());
            rt.setDescription(r.getDescription());
            rt.setMethod(r.getMethod());
            rt.setUri(r.getUri());
            rt.setType(r.getType());
            rt.setState(r.getState());
            rt.setLogout(r.getLogout());
            rt.setCreatedAt(r.getCreatedAt());
            rt.setUpdatedAt(r.getUpdatedAt());
            rt.setDeletedAt(r.getDeletedAt());

//            BeanUtils.copyProperties(r,rt);


            return rt;
        }).collect(Collectors.toList());
        responseT.setCode(0);
        responseT.setMessage("message");
        responseT.setResources(resources);
        TSerializer serializer = new TSerializer(factory);
        return serializer.serialize(responseT);
    }
    @Benchmark
    public ResourceT c1(){
        ResourceT rt = new ResourceT();
        rt.setId(rb.getId());
        rt.setName(rb.getName());
        rt.setCode(rb.getCode());
        rt.setDescription(rb.getDescription());
        rt.setMethod(rb.getMethod());
        rt.setUri(rb.getUri());
        rt.setType(rb.getType());
        rt.setState(rb.getState());
        rt.setLogout(rb.getLogout());
        rt.setCreatedAt(rb.getCreatedAt());
        rt.setUpdatedAt(rb.getUpdatedAt());
        rt.setDeletedAt(rb.getDeletedAt());
        return rt;
    }
    @Benchmark
    public ResourceT c2(){
        ResourceT rt = new ResourceT();
        BeanUtils.copyProperties(rb,rt);
        return rt;
    }


}
