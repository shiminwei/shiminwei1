package com.ahzd.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用于实现mongoDB自增id
 * @author admin
 *
 */
@Document(collection = "sequence")
public class SequenceId {

    @Id
    private String id;

    /**
     * 当前id
     */
    @Field("seq_id")
    private long seqId;

    /**
     * 所属集合
     */
    @Field("coll_name")
    private String collName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSeqId() {
		return seqId;
	}

	public void setSeqId(long seqId) {
		this.seqId = seqId;
	}

	public String getCollName() {
		return collName;
	}

	public void setCollName(String collName) {
		this.collName = collName;
	}

}