<template>
  <div>
      <Header />
      <div class="container">
        <div style="display: flex; justify-content: space-between;">
          <h1>입양신청목록</h1>
          <div class="display: flex;">
            <!-- <input type="text" style="border: 1px solid black; width: 90%; placeholder: '검색'; margin-right: 5px;"/>
            <span class="fas fa-question"></span> -->
            <!-- <input name="txtName" id="txtName">
            <span class="fas fa-question errspan"></span> -->
            <div class="input-wrapper">
              <input type="text" placeholder="검색" />
            </div>
          </div>
        </div>
        <table class="adoption-table">
          <thead class="adoption-thead">
            <tr>
              <th style="width: 20%;">번호</th>
              <th style="width: 60%;">제목</th>
              <th style="width: 10%;">글쓴이</th>
              <th style="width: 10%;">작성일</th>
            </tr>
          </thead>
          <tbody class="adoption-tbody">
            <tr>
              <td>1</td>
              <td>말티즈 입양하고 싶어요</td>
              <td>김싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>2</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>3</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>
            <tr>
              <td>4</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>5</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>6</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>7</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>8</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>9</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>10</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

            <tr>
              <td>11</td>
              <td>이 아이를 입양하고 싶습니다</td>
              <td>이싸피</td>
              <td>2020.10.29</td>
            </tr>

          </tbody>
        </table>
      </div>
  </div>
</template>

<script>
import Header from '../components/Header.vue'
export default {
  name: 'AdoptionList',
  data() {
    return {
      adoptionNumber: "",
      title: "",
      writer: "",
      adoptionDay: "",
    }
  },
  components: {
    Header,
  },
  methods: {

  },
  created: {
   
  }
}
</script>

<style>
  .table {
    margin-top: 20px;
  }

  .adoption-table {
    border-top: 1px solid black;
    border-collapse: collapse;
    width: 100%;
  }

  th, td {
    border-bottom: 1px solid black;
  }

  th {
    text-align: left;
  }

  .errspan {
    border: 1px solid black;
    float: right;
    margin-right: 6px;
    margin-top: -20px;
    position: relative;
    z-index: 2;
    width: 50%;
  }

  .input-wrapper {
    display:inline-block;
    position: relative;
    border: 1px solid black;
  }

.input-wrapper:after {
    font-family: 'FontAwesome';
    content: '\f128';
    position: absolute;
    right: 6px;
  }
</style>