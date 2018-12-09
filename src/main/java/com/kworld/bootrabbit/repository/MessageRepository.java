package com.kworld.bootrabbit.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kworld.bootrabbit.entity.Message;

@Repository
public class MessageRepository {
	
	private static final Logger log = LoggerFactory.getLogger(MessageRepository.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public int insert(Message message) {
		log.info("Insert Message Start");
		try {
			return this.namedParameterJdbcTemplate.update("insert into message(id, message) values(:id, :message)",
					new MapSqlParameterSource("id", message.getUuid()).addValue("message", message.getMsg()));
		} finally {
			log.info("Insert Message End");
		}
	}
	
	public List<Message> fetchMessages(){
		log.info("Fetching Messages");
		return this.namedParameterJdbcTemplate.query("select id, message from message ", new ResultSetExtractor<List<Message>>() {
			List<Message> messages = new ArrayList<>();
			@Override
			public List<Message> extractData(ResultSet rs) throws SQLException {
				while(rs.next()) {
					Message message = new Message();
					message.setUuid(rs.getString("id"));
					message.setMsg(rs.getString("message"));
					messages.add(message);
				}
				return this.messages;
			}
		});
	}
}
