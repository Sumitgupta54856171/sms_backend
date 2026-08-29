CREATE TABLE enrollment_session
(
    enrollment_id            BIGINT            NOT NULL,
    student_id               BIGINT,
    session_id_in_enrollment BIGINT,
    class_no                 VARCHAR(255),
    roll_no                  VARCHAR(255),
    total_fees               BIGINIT DEFAULT 0 NOT NULL,
    due_fees                 BIGINT  DEFAULT 0 NOT NULL,
    discount_fees            BIGINT  DEFAULT 0 NOT NULL,
    CONSTRAINT pk_enrollment_session PRIMARY KEY (enrollment_id)
);

ALTER TABLE enrollment_session
    ADD CONSTRAINT FK_ENROLLMENT_SESSION_ON_SESSION_ID_IN_ENROLLMENT FOREIGN KEY (session_id_in_enrollment) REFERENCES session (session_id);

ALTER TABLE enrollment_session
    ADD CONSTRAINT FK_ENROLLMENT_SESSION_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);