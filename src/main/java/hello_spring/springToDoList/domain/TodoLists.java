package hello_spring.springToDoList.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "todolists")
public class TodoLists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private Long listId;

   @Column(name = "user_id")
    private Long userId;

    @Column(name = "list_title")
    private String listTitle;

    @Column(name = "list_description")
    private String listDescription;

    public TodoLists(){}

    public TodoLists(Long userId, String listTitle, String listDescription){
        this.userId = userId;
        this.listTitle = listTitle;
        this.listDescription = listDescription;
    }

    // Getter Setter
    public Long getListId(){
        return listId;
    }
    public void setListId(Long listId){
        this.listId = listId;
    }
    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId){
        this.userId = userId;
    }
    public String getListTitle(){
        return listTitle;
    }
    public void setListTitle(String listTitle){
        this.listTitle = listTitle;
    }
    public String setListDescription() {
        return listDescription;
    }
    public void getListDescription(String listDescription){
        this.listDescription=listDescription;
    }
}
