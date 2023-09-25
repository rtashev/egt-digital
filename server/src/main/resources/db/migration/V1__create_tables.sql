CREATE TABLE user_sessions (
                                 id INT auto_increment NOT NULL AUTO_INCREMENT,
                                 session_id varchar(256) NOT NULL,
                                 request_id varchar(256) NOT NULL,
                                 user_id varchar(256),
                                 api_response TEXT,
                                 insert_timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 CONSTRAINT user_sessions_PK PRIMARY KEY(id),
                                 CONSTRAINT session_id_request_id_UK UNIQUE KEY(session_id, request_id),
                                 INDEX user_id_IX(user_id)
)
ENGINE=InnoDB;