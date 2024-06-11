create table cart
(
    cartId     int auto_increment comment '购物车ID'
        primary key,
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create table productimage
(
    productImageId        int auto_increment comment '商品图片ID'
        primary key,
    productImageSrc       varchar(255) not null comment '商品图片地址',
    productImageProductId int          not null comment '图片对应商品ID'
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create index product_image_product_id
    on productimage (productImageProductId);

create table user
(
    userId         int auto_increment comment '用户ID'
        primary key,
    userName       varchar(25)                         not null comment '用户名',
    userAccount    varchar(50)                         not null comment '用户账号',
    userPassword   varchar(255)                        not null comment '用户密码',
    userAvatar     varchar(100)                        null comment '用户头像',
    userRole       varchar(10)                         not null comment '用户角色 user/admin/provider',
    isDelete       tinyint   default 0                 not null comment '是否删除 0-正常 1-删除',
    userCreateDate timestamp default CURRENT_TIMESTAMP not null comment '用户创建时间',
    userUpdateDate timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '用户最近更新时间',
    cartId         int                                 not null comment '用户对应的购物车id',
    constraint user_cart_cart_id_fk
        foreign key (cartId) references cart (cartId)
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create table address
(
    addressAreaId int auto_increment comment '地区ID'
        primary key,
    addressName   mediumtext        not null comment '详细地址信息',
    addressUserId int               not null comment '创建地址的用户ID',
    isDelete      tinyint default 0 not null comment '是否删除',
    constraint address_ibfk_1
        foreign key (addressUserId) references user (userId)
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create index address_user_id
    on address (addressUserId);

create table order_
(
    orderId       int auto_increment comment '订单ID'
        primary key,
    orderCode     varchar(30)                         not null comment '订单编号',
    orderAddress  int                                 not null comment '订单对应地址ID',
    orderReceiver varchar(20)                         not null comment '收件人',
    orderMobile   varchar(15)                         not null comment '收件人手机号',
    orderPayDate  timestamp default CURRENT_TIMESTAMP not null comment '下单时间',
    orderStatus   tinyint(1)                          not null comment '订单状态 0-待支付 1-待发货 2-待收货 3-已收货 4-已取消',
    orderUserId   int                                 not null comment '订单对应用户ID',
    isDelete      tinyint   default 0                 not null comment '是否删除',
    constraint order_code
        unique (orderCode),
    constraint order__ibfk_3
        foreign key (orderUserId) references user (userId),
    constraint order_address_addressAreaId_fk
        foreign key (orderAddress) references address (addressAreaId)
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create index order_address
    on order_ (orderAddress);

create index order_user_id
    on order_ (orderUserId);

create table product
(
    productId           int auto_increment comment '商品ID'
        primary key,
    productName         varchar(100)                             not null comment '商品名称',
    productPrice        decimal(10, 2) default 0.00              not null comment '商品价格',
    productCategoryName varchar(50)                              not null comment '商品所属分类名称',
    productIsEnabled    tinyint(1)     default 0                 not null comment '商品是否启用 0-启用 1-未启用',
    stock               int            default 0                 not null comment '库存',
    isDelete            tinyint        default 0                 not null comment '是否删除',
    providerId          int                                      not null comment '供应商ID',
    productDescription  mediumtext                               not null comment '商品描述',
    productCreateDate   timestamp      default CURRENT_TIMESTAMP not null comment '商品创建时间',
    productUpdateDate   timestamp      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '商品最近更新时间',
    constraint product_ibfk_1
        foreign key (providerId) references user (userId)
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create table cartproduct
(
    cartId        int           not null comment '购物车ID',
    productId     int           not null comment '商品ID',
    quantity      int default 1 not null comment '数量',
    cartProductId int auto_increment comment '主键'
        primary key,
    constraint cart_product_ibfk_1
        foreign key (cartId) references cart (cartId),
    constraint cart_product_ibfk_2
        foreign key (productId) references product (productId)
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create index cart_id
    on cartproduct (cartId);

create index product_id
    on cartproduct (productId);

create table comment
(
    commentId         int auto_increment comment '评论ID'
        primary key,
    commentContent    mediumtext                          not null comment '评论内容',
    commentCreateDate timestamp default CURRENT_TIMESTAMP not null comment '评论创建时间',
    commentUserId     int                                 not null comment '评论对应用户ID',
    commentProductId  int                                 not null comment '评论对应商品ID',
    isDelete          tinyint   default 0                 not null comment '是否删除',
    constraint comment_ibfk_1
        foreign key (commentProductId) references product (productId),
    constraint comment_ibfk_2
        foreign key (commentUserId) references user (userId)
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create index comment_product_id
    on comment (commentProductId);

create index comment_user_id
    on comment (commentUserId);

create table orderitem
(
    id                  int auto_increment comment '主键，自增'
        primary key,
    orderId             int                                 not null comment '订单ID，外键，指向order表',
    productId           int                                 not null comment '产品ID，外键，指向product表',
    quantity            int                                 not null comment '产品数量',
    price               decimal(10, 2)                      not null comment '产品价格',
    orderItemCreateDate timestamp default CURRENT_TIMESTAMP null comment '订单项创建时间',
    orderItemUpdateDate timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '订单项更新时间',
    productName         varchar(128)                        not null comment '商品名称',
    constraint orderitem_ibfk_1
        foreign key (orderId) references order_ (orderId)
            on update cascade on delete cascade,
    constraint orderitem_ibfk_2
        foreign key (productId) references product (productId)
            on update cascade on delete cascade
)
    comment '订单项表';

create index orderId
    on orderitem (orderId);

create index productId
    on orderitem (productId);

create index provider_id
    on product (providerId);

create table shop
(
    shopId      int auto_increment comment '主键'
        primary key,
    name        varchar(50)  not null comment '商铺名称',
    description varchar(200) null comment '商铺描述',
    shopUserId  int          not null comment '商铺所属用户ID',
    constraint shop_ibfk_1
        foreign key (shopUserId) references user (userId)
)
    collate = utf8mb4_bin
    row_format = DYNAMIC;

create index shop_user_id
    on shop (shopUserId);

