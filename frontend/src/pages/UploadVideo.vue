<template>
  <div>
    <Header />
    <v-container
      style="padding-top: 90px; background-color: rgb(244, 236, 225)"
    >
      <h1>동영상 업로드 게시판</h1>
      <v-col>
      <v-text-field
        label="DesertionNO"
        v-model="desertionNo"
        placeholder="일련번호를 입력하세요"
        outlined
      ></v-text-field>
      </v-col>
      <v-col>
      <v-text-field
        label="제목"
        v-model="title"
        placeholder="제목을 입력하세요"
        outlined
      ></v-text-field>
      </v-col>
      <v-col>
        <v-file-input
          v-model="file"
          color="deep-purple accent-4"
          counter
          label="File input"
          placeholder="Select your files"
          prepend-icon="mdi-paperclip"
          outlined
          :show-size="1000"
        >
          <template v-slot:selection="{ index, text }">
            <v-chip
              v-if="index < 2"
              color="deep-purple accent-4"
              dark
              label
              small
            >{{ text }}</v-chip>
            <span
              v-else-if="index === 2"
              class="overline grey--text text--darken-3 mx-2"
            > +{{ files.length - 2 }} File(s)</span>
          </template>
        </v-file-input>
      </v-col>
      <v-col>
      <v-textarea
        outlined
        name="input-7-4"
        label="내용"
        v-model="content"
        placeholder="내용을 입력하세요"
      ></v-textarea>
      </v-col>
      <v-col>
        <v-btn
        outlined
        color="indigo"
        @click="submit"
        >등록하기</v-btn>
      </v-col>
    </v-container>
  </div>
</template>
<script>
import Header from '../components/Header.vue'
import SERVER from "@/api/url"
import axios from 'axios'

export default {
  components: {
    Header
  },
  data(){
    return{
      writer:'',
      title:'',
      content:'',
      file:'',
      desertionNo:''
    }
  },
  created(){
    axios.get(SERVER.URL + '/admin/uploadVideo',{
      headers:{
        Authorization : this.$cookies.get("accessToken")
      }
    })
    .then((res)=>{
      this.writer = res.data.user_id;
      console.log(res)
    })
    .catch((err)=>{
      console.log("ERROR다 ERROR : "+err)
    })
  },
  methods:{
    submit(){
      console.log(this.desertionNo,' ',this.title,' ',this.file,' ',this.content,' ',this.writer)
      const formData = new FormData();
          formData.append("desertion_no", this.desertionNo);
          formData.append("title", this.title);
          formData.append("file", this.file);
          formData.append("content",this.content);
          formData.append("writer", this.writer);
      axios.post(SERVER.URL + '/admin/uploadVideo/', {
        "mfile": formData
        }, {
        headers:{
          Authorization: this.$cookies.get("accessToken"),
          "Content-Type": "multipart/form-data",
        }
      })
      .then((res)=>{
        console.log(res)
      })
      .catch((err)=>{
        console.log(err)
      })
    }
  }
};
</script>

<style>
</style>
