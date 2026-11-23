/* 유저 */
create table users(
    u_id varchar2 (15 char) primary key,
    u_name varchar2 (10 char) not null,
    u_password varchar2 (20 char) not null,
    u_img varchar2 (30 char) not null,
    u_phone varchar2 (15 char) not null,
    u_mail varchar2 (50 char) not null
);

create sequence users_seq;

--  보드
create table board(
    b_no number(5) primary key ,
    b_title varchar2 (50 char) not null,
    b_content varchar2 (500 char) not null,
    b_view number(6) not null,
    b_created_date date not null,
    b_updated_date date not null,
    b_status varchar2(1 char) not null,
    b_u_id varchar2(15 char) not null,
    b_region_no number(3) not null,
    b_cg_no number(2) not null,
    constraint board_users
                  foreign key (b_u_id) references users(u_id),
    constraint board_region
                  foreign key (b_region_no) references region(r_no),
    constraint board_category
                  foreign key (b_cg_no) references category(CG_NO)
);

create sequence board_seq;

-- 댓글
create table comments(
    c_no number(4) primary key ,
    c_content varchar2 (500 char) not null,
    c_created_date date not null ,
    c_updated_date date not null ,
    c_status varchar2(1 char) not null,
    c_b_no number(5) not null,
    c_parent_comment number(4) not null,
    c_u_id varchar2(15 char) not null,
    constraint comments_board
                     foreign key(c_b_no) references board(b_no),
    constraint comments_comments
                     foreign key (c_parent_comment) references comments(c_no),
    constraint comments_user
                     foreign key (c_u_id) references users(u_id)
);

create sequence comments_seq;

-- 글 좋아요
create table board_like(
    b_l_no number(6) primary key ,
    b_l_created_date date not null ,
    b_l_b_no number(5) not null ,
    b_l_u_id varchar2(15 char) not null,
    constraint board_like_board
                       foreign key (b_l_b_no) references board(b_no),
    constraint board_like_users
                       foreign key (b_l_u_id) references users(u_id)
);

create sequence board_like_seq;

-- 전체 일정
create table travel(
    t_no number(6) primary key ,
    t_r_no number(3) not null ,
    t_u_id varchar2(15 char)not null,
    t_title varchar2(50 char) not null ,
    t_img varchar2(50 char) not null,
    t_start_date date not null,
    t_end_date date not null,
    t_created_date date not null,
    t_updated_date date not null,
    constraint travel_region
                   foreign key (t_r_no) references REGION(r_no),
    constraint travel_users
                   foreign key (t_u_id) references users(u_id)
);

create sequence travel_seq;

-- 일정 스크랩
create table travel_scrap(
    t_s_no number(6) primary key ,
    t_s_t_no number(6) not null ,
    t_s_u_id varchar2(15 char) not null ,
    t_s_created_date date not null ,
    constraint travel_scrap_travel
                         foreign key (t_s_t_no) references travel(t_no),
    constraint travel_scrap_users
                         foreign key (t_s_u_id) references users(u_id)
);

create sequence travel_scrap_seq;

-- 일정 좋아요
create table travel_like(
    t_l_no number(6) primary key ,
    t_l_t_no number(6) not null ,
    t_l_u_id varchar2(15 char) not null ,
    t_l_created_date date not null ,
    constraint travel_like_travel
                         foreign key (t_l_t_no) references travel(t_no),
    constraint travel_like_users
                         foreign key (t_l_u_id) references users(u_id)
);

create sequence travel_like_seq;

-- 스케줄 장소
create table schedule_place(
    s_p_no number(7) primary key ,
    s_p_s_no number(7) not null ,
    s_p_spot_no number(7) not null ,
    s_p_visit_order number(2) not null,
    s_p_price number(7) not null ,
    constraint schedule_place_schedule
                           foreign key (s_p_s_no) references SCHEDULE (S_NO),
    constraint schedule_place_spot
                           foreign key (s_p_spot_no) references SPOT(SPOT_NO)
);

create sequence schedule_place_seq;

-- 지역
create table big_area(
    ba_no number(3) primary key ,
    ba_r_no number(3) not null,
    ba_img varchar2(50 char) not null,
    ba_name varchar2(50 char) not null,
    ba_info varchar2(300 char) not null,
    constraint big_area_region
                     foreign key (ba_r_no) references REGION(R_NO)
);

create sequence big_area_seq;

-- 특산품
create table product(
    p_no number(4) primary key ,
    p_ba_no number(3) not null ,
    p_sa_no number(5) not null,
    p_img varchar2(50 char) not null,
    p_name varchar2(30 char) not null,
    p_price number(7) not null,
    p_info varchar2(300 char) not null,
    constraint product_big_area
                    foreign key (p_ba_no) references BIG_AREA(ba_no),
    constraint product_small_area
                    foreign key (p_sa_no) references SMALL_AREA(SA_NO)
);

create sequence product_seq;

-- 이벤트
create table event(
    e_no number(4) primary key ,
    e_ba_no number(3) not null ,
    e_sa_no number(5) not null ,
    e_img varchar2(50 char) not null ,
    e_name varchar2(50 char) not null ,
    e_place varchar2(20 char) not null ,
    e_date date not null ,
    e_info varchar2(4000 char) not null,
    constraint event_big_area
                  foreign key (e_ba_no) references BIG_AREA(ba_no),
    constraint event_small_area
                  foreign key (e_sa_no) references SMALL_AREA(SA_NO)
);

create sequence event_seq;

-- 특산물 좋아요
create table product_like(
    p_l_no number(6) primary key ,
    p_l_u_id varchar2(15 char) not null ,
    p_l_p_no number(4) not null,
    p_l_created_date date not null ,
    constraint product_like_users
                         foreign key (p_l_u_id) references USERS(u_id),
    constraint product_like_product
                         foreign key (p_l_p_no) references product(p_no)
);

create sequence product_like_seq;

-- 이벤트 좋아요
create table event_like(
   e_l_no number(6) primary key ,
   e_l_u_id varchar2(15 char) not null ,
   e_l_e_no number(4) not null,
   e_l_created_date date not null ,
   constraint event_like_users
                        foreign key (e_l_u_id) references USERS(u_id),
   constraint event_like_event
                        foreign key (e_l_e_no) references event(e_no)
);

create sequence event_like_seq;