SELECT post_id
FROM (
         SELECT p.post_id,
                (SELECT COUNT(*)
                 FROM comment co
                 WHERE co.post_id = p.post_id) AS cnt_comments
         FROM post p
     ) t
WHERE t.cnt_comments <= 1
ORDER BY t.post_id ASC
    LIMIT 10;