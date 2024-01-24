

INSERT INTO roles
SELECT *
FROM (
         VALUES
             ROW(1,'ROLE_ADMIN'),
             ROW(2,'ROLE_USER')
     ) source_data
WHERE NOT EXISTS (
    SELECT NULL
    FROM roles
);