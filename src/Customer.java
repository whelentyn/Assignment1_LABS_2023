public class Customer {

    public Customer(Long id, Integer tier, String name) {
        this.id = id;
        this.tier = tier;
        this.name = name;
    }

    private Long id;
    private Integer tier;

    private String name;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

}

