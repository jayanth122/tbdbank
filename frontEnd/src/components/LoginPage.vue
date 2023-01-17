<template>
  <div class="container">
    <div class="row d-flex justify-content-center align-items-center">
      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
        <div class="card bg-dark text-white" style="border-radius: 1rem">
          <div class="card-body p-5 text-center">
            <div class="mb-md-5 mt-md-4 pb-5">
              <h2 class="fw-bold mb-2 text-uppercase">Login</h2>
              <p class="text-white-50 mb-5">Please login to your account</p>
              <form v-on:submit.prevent="submitForm">
                <div class="form-outline form-group form-white mb-4">
                  <input
                    type="text"
                    id="typeEmailX"
                    class="form-control form-control-lg"
                    placeholder="Username or CardNumber"
                    v-model="form.userName"
                  />
                </div>

                <div class="form-outline form-group form-white mb-4">
                  <!-- <label class="form-label" for="typePasswordX">Password</label> -->
                  <input
                    type="password"
                    id="typePasswordX"
                    class="form-control form-control-lg"
                    placeholder="Password"
                    v-model="form.password"
                  />
                </div>
                <p class="small mb-5 pb-lg-2">
                  <a class="text-light" href="#!">Forgot password?</a>
                </p>

                <button class="btn btn-outline-light btn-lg px-5" type="submit">
                  Login
                </button>
              </form>
              <div>
                <p class="mt-5">
                  Don't have an account?
                  <a href="#!" class="text-white-50 fw-bold">Sign Up</a>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "LoginPage",
  data() {
    return {
      form: {
        userName: "",
        cardNumber: "",
        password: "",
      },
    };
  },
  methods: {
    async submitForm() {
      let regex = new RegExp("^[0-9]{16}$");
      let temp = this.form.userName;
      if (regex.test(this.form.userName)) {
        this.form.userName = "";
        this.form.cardNumber = temp;
      }
      const response = await axios.post(
        "http://localhost:8081/login",
        JSON.stringify(this.form),
        {
          headers: {
            "content-type": "application/json",
          },
        }
      );
      if (response.data.success) {
        alert("Welcome");
      } else {
        this.$router.replace({ name: "LoginPage" });
      }
    },
  },
};
</script>
<style scoped>
body {
  padding-top: 0px;
  margin-top: 0px;
}
.gradient-custom {
  /* fallback for old browsers */
  background: #50583a;

  /* Chrome 10-25, Safari 5.1-6 */
  /* background: -webkit-linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1)); */

  /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
  /* background: linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1)) */
}
</style>