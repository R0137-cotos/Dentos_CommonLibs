SELECT
  1
FROM
 RESPONSIBLE_SALES
WHERE
 HANSH_CD IN (SELECT
                M150_HANSH.HANSH_CD
              FROM
                RESPONSIBLE_SALES M150_HANSH
              WHERE
                M150_HANSH.SINGLEUSER_ID = :singleUserId)
 AND SINGLEUSER_ID = :singleUserId