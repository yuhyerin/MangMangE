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
        <div style="display: flex; height: 60px;">
          <img :src="image" alt="이미지" style="height: 60px; width: 60px; margin: 0 1% 1% 0; border-radius: 20px;">
            <v-text-field
              placeholder="일련번호"
              v-model="desertionNo"
              outlined
            ></v-text-field>
            <v-btn 
              small
              elevation="0"
              style="padding: 0; background: rgba(255, 255, 255, 0);"
            >
              <v-icon style="color: green" :disabled="desertionNo.length < 1 || desertionNoCheck === 1 || desertionNoExist === 1">
                mdi-checkbox-marked-circle
              </v-icon>
            </v-btn>
        </div>
      <label v-if="desertionNoCheck" style="color: orange;">일련번호 길이는 15입니다!</label>
      <label v-if="desertionNoExist" style="color: orange;">존재하지 않는 일련번호입니다!</label>

      <div style="display: flex; margin-top: 25px;">
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

      <div style="display: flex;">
        <input type="file" ref="file" @change="selectFile" style="border: 1px solid gray; border-radius: 3px;" />
        <v-btn
          class="mx-2"
          small
          elevation="0"
          style="padding: 0; background: rgba(255, 255, 255, 0);"
        >
          <v-icon style="color: green" :disabled="!selectedFiles">
            mdi-checkbox-marked-circle
          </v-icon>
        </v-btn>
        <label v-if="selectedFileCheck" style="color: orange;">이미 업로드된 파일입니다</label>
      </div>

      <div style="display: flex; padding-top: 12px; height: 190px;">
        <v-textarea
          :counter="300"
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
          :disabled="!selectedFiles ||
                    selectedFileCheck === 1||
                    desertionNo.length < 1 ||
                    desertionNoCheck === 1 ||
                    desertionNoExist === 1 ||
                    title.length < 1 ||
                    content.length < 1 ||
                    contentCheck === 1"
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
      selectedFiles: 0,
      selectedFileCheck: 0,
      desertionNoCheck: 0,
      desertionNoExist: 0,
      contentCheck: 0,
      err:{
        message:'',
        fileMessage:''
      },
      image: require(`@/assets/image/merong1.png`)
    }
  },
  watch: {
    desertionNo() {
      if (this.desertionNo.length < 15 || this.desertionNo.length > 15) {
        this.desertionNoCheck = 1
        this.desertionNoExist = 0
      } else {
        this.desertionNoCheck = 0
      }
      console.log(this.desertionNo)
      this.image = require(`@/assets/image/merong1.png`)
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
            this.err.message = '없는 번호입니다.'
            this.desertionNoExist = 1
          }
          else{
            this.image = res.data.image;
            this.desertionNoCheck = false;
          }
        })
        .catch((err)=>{
          console.log(err)
        });
        });
      }
    },

    file(){
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
          this.err.fileMessage = '이미 존재하는 파일입니다.'
          this.selectedFiles = 0
          this.selectedFileCheck = 1
        }
      })
      .catch((err)=>{
        console.log(err)
        })
      });
    },

    content() {
      if (this.content.length <= 300) {
        this.contentCheck = 0
      } else {
        this.contentCheck = 1
      }
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