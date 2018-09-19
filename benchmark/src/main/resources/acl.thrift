namespace java io.jing.server.thrift

struct ResourceT {
    1: i64 id;
    2: optional string name;
    3: optional string code;
    4: optional string description;
    5: optional string method;
    6: optional string uri;
    7: optional i32 type;
    8: optional i32 state;
    9: optional bool logout;
}

struct ResponseT {
    1: i32 code;
    2: string message;
    3: list<ResourceT> resources;
}

service acl {
    ResponseT myResource()
}

