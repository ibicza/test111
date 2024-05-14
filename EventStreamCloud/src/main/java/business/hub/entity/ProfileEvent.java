    package business.hub.entity;


    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.Objects;
    import java.util.UUID;

    /**
     * author Igor Ostrovsky
     * Entity for saving to DataBase with UUID
     */
    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    public class ProfileEvent {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Column
        private String accountId;

        @Column
        private String firstName;

        @Column
        private String lastName;

        public ProfileEvent(String accountId, String firstName, String lastName) {
            this.accountId = accountId;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProfileEvent that = (ProfileEvent) o;
            return Objects.equals(id, that.id) && Objects.equals(accountId, that.accountId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, accountId, firstName, lastName);
        }
    }
