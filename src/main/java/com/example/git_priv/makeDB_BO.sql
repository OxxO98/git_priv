create table region (
    r_no number(3) primary key,
    r_name varchar2(50 char) not null,
    r_country varchar2(50 char) not null
);

create sequence region_seq;

create table category (
    cg_no number(4) primary key,
    cg_name varchar2(15 char) not null
);

create sequence category_seq;

create table image (
    i_no number(6) primary key ,
    i_b_no number(5) not null,
    i_url varchar2(50 char) not null ,
    i_sequence number(2) not null,
    constraint image_board foreign key (i_b_no) references Board(b_no)
);

create sequence image_seq;

create table board_scrap (
    b_s_no number(5) primary key ,
    b_s_b_no number(5) not null,
    b_s_u_id varchar2(15 char) not null,
    b_s_created_date date not null,
    constraint board_scrap_board foreign key (b_s_b_no) references BOARD(b_no),
    constraint board_scrap_userd foreign key  (b_s_u_id) references Users(U_ID)
);

create sequence board_scrap_seq;

create table spot (
  spot_no number(7) primary key ,
  spot_name varchar2(50 char) not null ,
  spot_address varchar2(50 char) not null ,
  spot_latitude number(10, 6) not null ,
    spot_longitude number(10, 6) not null,
    spot_spotcode varchar2(30 char) not null ,
    spot_phone varchar2(15 char) not null,
    spot_postal varchar2(10 char) not null,
    spot_category varchar2(2 char) not null,
    spot_subcategory varchar2(10 char) not null
);

create sequence spot_seq;

create table schedule (
    s_no number(7) primary key ,
    s_t_no number(6) not null ,
    s_date date not null ,
    s_day number(2) not null,
    constraint schedule_travel foreign key (s_t_no) references Travel(t_no)
);

create sequence schedule_seq;

create table route (
    rt_no number(7) primary key ,
    rt_s_no number(6) not null,
    rt_spot_no_from number(7) not null ,
    rt_spot_no_to number(7) not null,
    rt_distance varchar2(30 char) not null,
    rt_duration varchar2(30 char) not null,
    rt_transport_type varchar2(30 char) not null,
    rt_place number(7) not null,
    constraint route_schedule foreign key (rt_s_no) references schedule(s_no),
    constraint route_spot_from foreign key (rt_spot_no_from) references spot(spot_no),
    constraint route_spot_to foreign key (rt_spot_no_to) references Spot(spot_no)
);

create sequence route_seq;

create table small_area(
    sa_no number(5) primary key ,
    sa_ba_no number(3) not null ,
    sa_img varchar2(50 char) not null ,
    sa_name varchar2(30 char) not null ,
    sa_info varchar2(300 char) not null ,
    constraint small_area_big_area foreign key (sa_ba_no) references BIG_AREA(ba_no)
);

create sequence small_area_seq;

create table restaurant(
    rs_no number(4) primary key ,
    rs_ba_no number(3) not null,
    rs_sa_no number(5) not null ,
    rs_img varchar2(50 char) not null,
    rs_name varchar2(30 char) not null ,
    rs_place varchar2(50 char) not null,
    rs_time varchar2(30 char) not null,
    rs_call varchar2(15 char) not null,
    rs_info varchar2(300 char) not null,
    constraint restaurant_big_area foreign key (rs_ba_no) references BIG_AREA(ba_no),
    constraint restaurant_small_area foreign key (rs_sa_no) references SMALL_AREA(sa_no)
);

create sequence restaurant_seq;

create table restaurant_like(
    rs_l_no number(6) primary key ,
    rs_l_u_id varchar2(15 char) not null,
    rs_l_r_no number(4) not null,
    rs_l_created_date date not null ,
    constraint restaurant_like_users foreign key (rs_l_u_id) references users(u_id),
    constraint restaurant_like_restaurant foreign key (rs_l_r_no) references restaurant(rs_no)
);

create sequence restaurant_like_seq;
