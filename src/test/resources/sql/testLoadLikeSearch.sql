select id
     , moji
     , like_search_string
  from test_data
 where like_search_string like '%' || :likeSearchString || '%' escape '\';