package lab03;

class Link{
    public String ref;
    // in the future there will be more fields
    public Link(String ref) {
        this.ref=ref;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (ref != null && obj != null && obj instanceof Link) {
            Link other = (Link) obj;
 
            if (other.ref == null) return false;
            return ref.equals(other.ref);
        }
 
        return false;
    }
}