SELECT COUNT(*) AS cnt_no_posts
FROM profile p
WHERE NOT EXISTS (
    SELECT 1
    FROM post ps
    WHERE ps.profile_id = p.profile_id
);
