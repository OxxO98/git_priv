-- create table TRAVEL_INFO
-- (
--     info_no      number(4) primary key,
--     info_year    varchar2(10 char) not null,
--     info_region  varchar2(10 char) not null,
--     info_association    varchar2(50 char) not null,
--     info_title   varchar2(50 char) not null,
--     info_content varchar2(4000 char)
-- );

select * from TRAVEL_INFO Where length( info_content ) > 3990;
-- 5774, 3878 (4000 줄 이상)

-- 지역별 분포
Select INFO_REGION,
       count(*) as total_count,
       ceil(avg(length(INFO_CONTENT))) as avg_length,
       max(length(INFO_CONTENT)) as max_length,
       min(length(INFO_CONTENT)) as min_length
from TRAVEL_INFO
where instr(info_region, '・') = 0
group by INFO_REGION
order by total_count desc;

-- 평균 글자 길이
select avg(length(INFO_CONTENT))
from TRAVEL_INFO
where instr(info_region, '・') = 0;

-- 글자 길이 분포
SELECT length_range, COUNT(*) as count
from (SELECT CASE
                 WHEN length(INFO_CONTENT) <= 500
                     THEN '0~500'
                 WHEN length(INFO_CONTENT) <= 1000
                     THEN '501~1000'
                 WHEN length(INFO_CONTENT) <= 2000
                     THEN '1001~2000'
                 ELSE '2001 이상'
                 END AS length_range
      FROM TRAVEL_INFO
      WHERE INFO_CONTENT IS NOT NULL)
GROUP BY length_range;

-- title 만
select INFO_TITLE
from TRAVEL_INFO;

-- region 부정확함
select distinct info_region, count(*)
from travel_info
where instr(info_region, '・') = 0
group by info_region
order by info_region;

-- 여러 현에 걸쳐있는 목록
select *
from travel_info
where instr(info_region, '・') > 0
order by info_region;

update TRAVEL_INFO
set info_region = trim(INFO_REGION);

update TRAVEL_INFO
set info_region = '鹿児島県'
where info_region = '鹿児島';

select *
from TRAVEL_INFO
where info_region like '%茨城県%';
-- 없는 현 : 茨城県, 愛媛県


select *
from TRAVEL_INFO
where info_no = 954;

select info_no, info_title_ko, length( INFO_CONTENT_ko) as length, INFO_CONTENT_ko
from TRAVEL_INFO
where info_region_ko like '%오카야마%'
order by length;

-- 일본 현 이름 한국어로 변경
update TRAVEL_INFO
set INFO_REGION_KO = '홋카이도・아오모리・아키타'
where info_region = '北海道・青森県・秋田県';

select * from TRAVEL_INFO;
-- 일본 연호 서력으로 변경
update TRAVEL_INFO
set INFO_YEAR_KO = '2024년'
where info_year = '令和6年';
