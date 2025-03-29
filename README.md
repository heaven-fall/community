# community


[github oauth](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/creating-an-oauth-app)
[github oauth callback](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps)

### 脚本
```sql
create table USER
(
  ID           INTEGER auto_increment
    primary key,
  ACCOUNT_ID   CHARACTER VARYING(100),
  NAME         CHARACTER VARYING(50),
  TOKEN        CHARACTER(36),
  GMT_CREATE   BIGINT,
  GMT_MODIFIED BIGINT
);


```