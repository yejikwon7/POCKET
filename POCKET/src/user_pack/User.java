//package user_pack;
//
//class User {
//    private String id;
//    private String pw;
//    private String email;
//
//    public User(String id, String pw, String email) {
//        setId(id);
//        setPw(pw);
//        setEmail(email);
//    }
//    
//    public User(String id) {
//        setId(id);
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getPw() {
//        return pw;
//    }
//
//    public void setPw(String pw) {
//        this.pw = pw;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    
//    @Override
//    public boolean equals(Object o) {
//        if(o == null || !(o instanceof User)) {
//            return false;
//        }
//        User temp = (User)o;
//
//        return id.equals(temp.getId());
//    }
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//}