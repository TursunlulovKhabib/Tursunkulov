SELECT p.post_id
FROM post p
         JOIN (
    SELECT post_id
    FROM comment
    GROUP BY post_id
    HAVING COUNT(*) = 2
) c ON c.post_id = p.post_id
WHERE p.title ~ '^[0-9]'
  AND length(p.content) > 20
ORDER BY p.post_id ASC;
