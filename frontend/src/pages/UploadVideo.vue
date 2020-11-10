<template>
    <div>
    <Header/>
    <v-container
      style="padding-top: 90px; background-color: rgb(244, 236, 225)"
    >
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
        <input type="file" ref="file" @change="selectFile" />
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
        <v-btn outlined rounded
        :disabled="!selectedFiles" @click="upload"
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

  components :{
    Header
  },

  data(){
      return{
        desertionNo:'',
        title:'',
        file:undefined,
        content:'',
        selectedFiles: false
      }
  },
  methods:{
    selectFile() {
      console.log('selectFILE')
      this.file = this.$refs.file.files;
      this.selectedFiles = true;
    },
    upload(){
        var formData = new FormData();
        console.log(this.file[0].name)
        // formData.append("mfile", this.file[0], this.desertionNo+"_"+this.file[0].name);
        SERVER.tokenCheck(()=>{
          axios.post(SERVER.URL + '/admin/uploadVideo',
            {
              "desertion_no" : this.desertionNo,
              "title" : this.title,
              "content" : this.content,
            },
            {
              headers:{
                "Authorization" : this.$cookies.get("accessToken"),
                "contentType": 'application/json'
              }
            }
          )
          .then((res)=>{
            console.log(res.data)
            formData.append("mfile", this.file[0], res.data.uid+"_"+this.file[0].name);
            if(res.data.success==true){
              axios.post(SERVER.URL+'/admin/upload',formData,
            {
              headers: {
                  "Authorization" : this.$cookies.get("accessToken"),
                  "content-Type" : "multipart/form-data; charset=utf-8; boundary='calculated when request is sent';"
              }
            })
            .then((res)=>{
              console.log('RES : ',res)
            })
            .catch((err)=>{
              console.log('ERROR : ',err)
            })
            }
          })
          .catch((err)=>{
            console.log('ERRORERROR : ',err)
          })
        });
    }
}
    
}
</script>