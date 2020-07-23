SELECT 1
  FROM RESPONSIBLE_SALES
 WHERE HANSH_CD IN (SELECT REF_HANSH.REF_HANSH_CD
                      FROM MV_REF_HANSH_SELC_TBL_MCL REF_HANSH
                     WHERE REF_HANSH.HANSH_CD IN (SELECT REF_HANSH_SELC.HANSH_CD
                                                    FROM RESPONSIBLE_SALES REF_HANSH_SELC
                                                   WHERE REF_HANSH_SELC.SINGLEUSER_ID = :targetSingleUserId)
                   )
   AND SINGLEUSER_ID = :editorSingleUserId