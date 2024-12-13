package hello_spring.springToDoList.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "todoitems")
public class TodoItems {
    @Id
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "list_id")
    private Long listId;
    @Column(name = "item_title")
    private String itemTitle;
    @Column(name = "item_description")
    private String itemDescription;
    @Column(name = "status_code")
    private Integer statusCode;

    public TodoItems(){}

    public TodoItems(Long listId, String itemTitle, String itemDescription, Integer statusCode){
        this.listId = listId;
        this.itemTitle = itemTitle;
        this.itemDescription=itemDescription;
        this.statusCode = statusCode;
    }
    // Getter Setter
    public Long getItemId(){
        return itemId;
    }
    public void setItemId(Long itemId){
        this.itemId = itemId;
    }
    public Long getListId(){
        return listId;
    }
    public void setListId(Long listId){
        this.listId = listId;
    }
    public String getItemTitle(){
        return itemTitle;
    }
    public void setItemTitle(String itemTitle){
        this.itemTitle = itemTitle;
    }
    public String getItemDescription(){
        return itemDescription;
    }
    public void setItemDescription(String itemDescription){
        this.itemDescription = itemDescription;
    }
    public Integer getStatusCode(){
        return statusCode;
    }
    public void setStatusCode(Integer statusCode){
        this.statusCode = statusCode;
    }
}
