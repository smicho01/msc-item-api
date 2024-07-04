
-- dummy Question data
INSERT INTO items.question (id, title, content, userId, userName, collegeId, moduleId, status) VALUES
('9a7138dd-871e-476a-b610-2bcac68a6a82', 'How to create LSTM', 'Content of how to create LSTM ...', 'c7288693-2bc4-4fd8-aca2-f9fe71f2a8ed', 'PertinentKingfisher', '69c72387-4477-4720-af08-1acf62d81470','27aeff8a-3cf5-438d-91e9-0ac59061e94c', 'PENDING'),
('9a7138dd-871e-476a-b610-2bcac68a6a55', 'How to create B+Tree', 'Content of how to create B+Tree ...', 'c7288693-2bc4-4fd8-aca2-f9fe71f2a8ed', 'PertinentKingfisher', '69c72387-4477-4720-af08-1acf62d81470','27aeff8a-3cf5-438d-91e9-0ac59061e94c', 'REJECTED');


-- dummy COLLEGE data
INSERT INTO items.college (id, name) VALUES
('69c72387-4477-4720-af08-1acf62d81470', 'Birkbeck, University of London'),
('12c23765-7f23-441a-b729-706629c7ab80', 'UCL - University College London');


-- dummy MODULE data
-- birkbeck
INSERT INTO items.module (id, name, collegeId) VALUES
('9a7138dd-871e-476a-b610-1bcac68a6c00', 'General question', '69c72387-4477-4720-af08-1acf62d81470'),
('9a7138dd-871e-476a-b610-2bcac68a6c42', 'Applied Machine Learning', '69c72387-4477-4720-af08-1acf62d81470'),
('bee588b6-f253-4188-a5ff-336d78b4373b', 'Information Security', '69c72387-4477-4720-af08-1acf62d81470'),
('66486e50-964f-4362-9628-10b90d32c865', 'Data Analysis Using R', '69c72387-4477-4720-af08-1acf62d81470'),
('a0c41a01-c40f-4677-a511-36598adbe501', 'Cloud Computing', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e94c', 'Advances in Data Management', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e14c', 'Natural Language Processing', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e24c', 'Internet and Web Technologies', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e34c', 'Introduction to Computer Science', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e44c', 'Introduction to Programming', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e54c', 'Software and Programming I', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e64c', 'Software and Programming II', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e74c', 'Software and Programming III', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e84c', 'Database Management', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e94a', 'Software Engineering I', '69c72387-4477-4720-af08-1acf62d81470'),
('27aeff8a-3cf5-438d-91e9-0ac59061e15c', 'Software Engineering II', '69c72387-4477-4720-af08-1acf62d81470');
-- ucl
INSERT INTO items.module (id, name, collegeId) VALUES
('82ceabc7-a69e-43df-9a3b-aed9a9dc7700', 'General question', '12c23765-7f23-441a-b729-706629c7ab80'),
('82ceabc7-a69e-43df-9a3b-ded9a9dc774a', 'Principles of Programming', '12c23765-7f23-441a-b729-706629c7ab80'),
('f5fc12b0-7007-450f-b2e3-c9f05674b829', 'Algorithms', '12c23765-7f23-441a-b729-706629c7ab80'),
('ad607a8b-9d00-4565-95b4-5ccffe4900fe', 'Discrete Mathematics for Computer Scientists', '12c23765-7f23-441a-b729-706629c7ab80'),
('1ca9a9a0-624c-401b-973c-abd0dcd1ff9e', 'Engineering Challenges', '12c23765-7f23-441a-b729-706629c7ab80');


-- dummy tags
INSERT INTO items.tag (id, name) VALUES
('82ceabc2-b69e-43df-9a3b-ded9a9dc7741', 'php'),
('f5fc12b1-5007-450f-b2e3-c9f05674b822', 'machine learning');

-- dummy tag_question
INSERT INTO items.tag_question (tag_id, question_id) VALUES
('82ceabc2-b69e-43df-9a3b-ded9a9dc7741', '9a7138dd-871e-476a-b610-2bcac68a6a82'),
('f5fc12b1-5007-450f-b2e3-c9f05674b822', '9a7138dd-871e-476a-b610-2bcac68a6a82');