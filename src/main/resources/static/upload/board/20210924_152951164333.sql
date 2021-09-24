select * from tb_member;

INSERT INTO 
	TB_MEMBER
	(
		EMAIL,
		NAME,
		PHONE,
		BIRTH,
		PASSWORD,
		DEPARTMENT,
		POSITION,
		EMAIL_KEY,
		EMAIL_VERIFY_PNTTM,
		FRST_REGIST_PNTTM,
		FRST_REGISTER_ID,
		LAST_UPDT_PNTTM,
		LAST_UPDUSR_ID,
		QUIT_AT
	)
VALUES
	(
		'bbb123@gmail.com',
		'박사원',
		'01012345050',
		'20210701',
		'1234',
		'개발팀',
		'사원',
		'1234',
		null,
		sysdate,
		'bbb123@gmail.com',
		sysdate,
		'bbb123@gmail.com',
		default
	);
	

commit;

	 
	 
