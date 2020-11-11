<template>
    <div style="padding-top: 75px" >
    <Header/>
    <v-container
      style="
        padding-top: 30px;
      "
    >
      <h2 style="text-align: center; padding-bottom: 12px;">동영상 업로드</h2>
      <v-col>
        <v-text-field
            label="일련번호"
            v-model="desertionNo"
            outlined
        ></v-text-field>
      </v-col>
      <v-col>
        <v-text-field
          label="제목"
          v-model="title"
          outlined
        ></v-text-field>
      </v-col>
      <v-col>
        <input type="file" ref="file" @change="selectFile" style="padding-bottom: 12px; border: 1px solid gray; border-radius: 3px;"/>
      </v-col>
      <v-col>
        <v-textarea
          outlined
          name="input-7-4"
          v-model="content"
          label="내용"
          style="padding-top: 24px;"
        ></v-textarea>
      </v-col>
      <v-col style="padding-top: 0px;">
        <v-btn 
          outlined
          rounded
          :disabled="!selectedFiles"
          @click="upload"
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
      desertionNo:'',
      title:'',
      file:undefined,
      content:'',
      selectedFiles: false,
      err:{
        message:'',
        fileMessage:''
      }
    }
  },
  watch: {
    desertionNo() {
      console.log(this.desertionNo)
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
            alert(this.err.message)
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
          alert(this.err.fileMessage)
        }
      })
      .catch((err)=>{
        console.log(err)
        // this.err.fileMessage = '이미 존재하는 파일입니다.'
        // alert(this.err.fileMessage)
      })
      });
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