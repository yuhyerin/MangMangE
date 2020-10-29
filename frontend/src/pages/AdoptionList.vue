<template>
  <div>
      <Header />
      <div class="table">
        <table>
          <thead>
            <tr>
              <th>이름</th>
              <th>나이</th>
              <th>성별</th>
              <th>100M 달리기</th>
              <th>윗몸 일으키기</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>홍길동</td>
              <td>22세</td>
              <td>남</td>
              <td>15.25</td>
              <td>29</td>
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
  components: {
    Header,
  }
}
</script>

<style>

</style>