<template>
    <div style="padding-top: 75px" >
    <Header/>
    <v-container
      style="
        padding-top: 30px;
        width: 50%;
      "
    >
      <h2 style="text-align: center; padding-bottom: 12px;">동영상 업로드</h2>
      <div style="display: flex; justify-content: center; align-items: center;">
        <img :src="image" alt="이미지" style="height: 75px; margin: 0 1% 1% 0; border-radius: 30px;">
        <v-text-field
          label="일련번호"
          v-model="desertionNo"
          outlined
          style="margin: 0; padding: 0;"
        ></v-text-field>
        <v-icon style="color: green" 
          :disabled="desertionNoCheck!=0">
          mdi-checkbox-marked-circle
        </v-icon>
      </div>
      <p v-if="desertionNoCheck==1" style="color: orange;">일련번호는 15자입니다.</p>
      <p v-else-if="desertionNoCheck==2" style="color: red;">존재하지 않는 일련번호입니다.</p>
      <div style="display: flex">
        <v-text-field
          placeholder="제목"
          v-model="title"
          outlined
        ></v-text-field>
        <v-btn
          class="mx-2"
          small
          elevation="0"
          style="padding: 0; background: rgba(255, 255, 255, 0);"
        >
          <v-icon style="color: green" :disabled="title.length < 1">
            mdi-checkbox-marked-circle
          </v-icon>
        </v-btn>
      </div>

      
      <div style="display: flex; padding-bottom: 12px;">
        <!-- <input type="file" ref="file" @change="selectFile" :disabled="desertionNoCheck !=0"/> -->
        <v-file-input
          ref="file"
          color="deep-purple accent-4"
          counter
          label="File input"
          placeholder="Select your files"
          prepend-icon="mdi-paperclip"
          outlined
          :show-size="1000"
          :change="selectFile"
          :disabled="desertionNoCheck !=0"
        >
    <template v-slot:selection="{ index, text }">
      <v-chip
        v-if="index < 2"
        color="deep-purple accent-4"
        dark
        label
        small
      >
        {{ text }}
      </v-chip>

      <span
        v-else-if="index === 2"
        class="overline grey--text text--darken-3 mx-2"
      >
        +{{ files.length - 2 }} File(s)
      </span>
    </template>
  </v-file-input>
        <v-btn
          class="mx-2"
          small
          elevation="0"
          style="padding: 0; background: rgba(255, 255, 255, 0);"
        >
          <v-icon style="color: green" :disabled="selectedFileCheck==0">
            mdi-checkbox-marked-circle
          </v-icon>
        </v-btn>
        <label v-if="selectedFileCheck" style="color: orange;">이미 업로드된 파일입니다</label>
      </div>
      <label v-if="selectedFileCheck==1" style="color:red">이미 업로드된 파일입니다</label>
      <label v-else-if="selectedFileCheck==2" style="color:red">지원하지 않는 파일 형식입니다.</label>
      <div style="display: flex;">
        <v-textarea
          :counter="500"
          outlined
          name="input-7-4"
          v-model="content"
          placeholder="내용"
          style="padding-top: 24px;"
        ></v-textarea>
        <v-btn
          class="mx-2"
          small
          elevation="0"
          style="margin-top: 10px; padding-top: 24px; background: rgba(255, 255, 255, 0);"
        >
          <v-icon style="color: green" :disabled="content.length < 1 || contentCheck === 1">
            mdi-checkbox-marked-circle
          </v-icon>
        </v-btn>
      </div>
      <label v-if="contentCheck" style="color: orange;">300자 이하로 입력해주세요</label>
      <div style="padding-top: 20px;">
        <v-btn 
          outlined
          rounded
          :disabled="selectedFileCheck!=0 || desertionNoCheck!=0 || title.length < 1 || content.length < 1"
          @click="upload"
        >등록하기</v-btn>
      </div>
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
      desertionNo:'',
      title:'',
      file: undefined,
      content:'',
      selectedFiles: false,
      desertionNoCheck: 1,
      selectedFileCheck: 0,
      image: require(`@/assets/image/merong1.png`),
      error:{
        message:'일련번호를 확인해주세요',
      }
    }
  },
  watch: {
    desertionNo() {
      this.image = require(`@/assets/image/merong1.png`)
      if(this.desertionNo.length<15)
      this.desertionNoCheck = 1;  // 일련번호는 15자입니다.
      if(this.desertionNo.length==15){
        SERVER.tokenCheck(() => {
        axios.get(SERVER.URL+'/admin/upload/checkNO',{
          params:{
            desertion_no : this.desertionNo
          },
          headers:{
            "Authorization" : this.$cookies.get("accessToken")
          }
        })
        .then((res)=>{
          console.log(res)
          if(res.status==202){
            console.log(res)
            this.desertionNoCheck=2;  // 존재하지 않는 일련번호입니다.
          }
          else{
            this.image = res.data.image;
            this.desertionNoCheck = 0;
            this.error.message = ''
          }
        })
        .catch((err)=>{
          console.log(err)
        });
        });
      }
    },

    file(){
      if(this.file[0].name.slice(-3)=='mp4'){
        SERVER.tokenCheck(() => {
        axios.get(SERVER.URL+'/admin/upload/checkFile',{
          params:{
            fileName : this.desertionNo+"_"+this.file[0].name
          },
          headers:{
            "Authorization" : this.$cookies.get("accessToken")
          }
        })
        .then((res)=>{
          console.log(res)
          if(res.status==202){
            this.selectedFileCheck = 1;
          }
          else{
            this.selectedFileCheck=0;
          }
        })
        .catch((err)=>{
          console.log(err)
        })
        });
      }
      else {
        this.selectedFileCheck = 2; 
      }
    }
  },

  methods:{
    selectFile() {
      console.log('selectFILE')
      this.file = this.$refs.file.files;
      console.log(this.file)
      console.log(this.file[0].filename)
      this.selectedFiles = true;
    },

    upload(){
        var formData = new FormData();
        console.log(this.desertionNo+'_'+this.file[0].name)
        SERVER.tokenCheck(()=>{
          axios.post(SERVER.URL + '/admin/uploadVideo',
            {
              "desertion_no" : this.desertionNo,
              "title" : this.title,
              "content" : this.content,
              "filepath" : this.desertionNo+'_'+this.file[0].name
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
              alert('등록되었습니다')
              this.$router.push("/videos")
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
  },
    
}
</script>

<style scoped>

</style>