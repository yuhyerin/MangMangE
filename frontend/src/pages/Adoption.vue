<template>
  <div>
    <Header />
    <div class="container" style="padding-top: 80px; width: 70%">
      <div v-if="pageTrigger">
        <AdoptionContact />
      </div>
      <div>
        <AdoptionForm />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Header from "../components/Header.vue";
import AdoptionContact from "../components/adoption/AdoptionContact.vue";
import AdoptionForm from "../components/adoption/AdoptionForm.vue";
import axios from "axios";
import SERVER from "@/api/url";

export default {
  name: "Adoption",
  components: {
    Header,
    AdoptionContact,
    AdoptionForm,
  },
  data() {
    return {
      pageTrigger: 0,
    };
  },
};
</script>
