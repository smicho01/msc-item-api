
-- dummy ITEMS data
INSERT INTO items.item(id, name, descr, userId) VALUES
('2cef2a1b-5ef9-4fc6-88fe-b2572f19ba1a', 'Item 01', 'descr tem01', '7cef2a1b-5ef9-4fc6-88fe-b2572f19bf65'),
('3d9d8092-b95d-4106-866f-96ef24902a2b', 'Item 02', 'descr item02', '4d9d8092-b95d-4106-866f-96ef2490252b'),
('a2e43664-2adf-4abd-97ee-3782e98c3a3b', 'Item 03', 'descr item03', 'f2e43664-2adf-4abd-97ee-3782e98c39e4');


-- dummy COLLEGE data
INSERT INTO items.college (id, name) VALUES
('69c72387-4477-4720-af08-1acf62d81470', 'Birkbeck, University of London'),
('12c23765-7f23-441a-b729-706629c7ab80', 'UCL - University College London');


-- dummy MODULE data
-- birkbeck
INSERT INTO items.module (id, name, collegeId) VALUES
('9a7138dd-871e-476a-b610-2bcac68a6c42', 'Applied Machine Learning', '69c72387-4477-4720-af08-1acf62d81470'),
('bee588b6-f253-4188-a5ff-336d78b4373b', 'Information Security', '69c72387-4477-4720-af08-1acf62d81470'),
('66486e50-964f-4362-9628-10b90d32c865', 'Data Analysis Using R', '69c72387-4477-4720-af08-1acf62d81470'),
('a0c41a01-c40f-4677-a511-36598adbe501', 'Cloud Computing', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e94c', 'Advances in Data Management', '69c72387-4477-4720-af08-1acf62d81470');
-- ucl
INSERT INTO items.module (id, name, collegeId) VALUES
('82ceabc7-a69e-43df-9a3b-ded9a9dc774a', 'Principles of Programming', '12c23765-7f23-441a-b729-706629c7ab80'),
('f5fc12b0-7007-450f-b2e3-c9f05674b829', 'Algorithms', '12c23765-7f23-441a-b729-706629c7ab80'),
('ad607a8b-9d00-4565-95b4-5ccffe4900fe', 'Discrete Mathematics for Computer Scientists', '12c23765-7f23-441a-b729-706629c7ab80'),
('1ca9a9a0-624c-401b-973c-abd0dcd1ff9e', 'Engineering Challenges', '12c23765-7f23-441a-b729-706629c7ab80');
