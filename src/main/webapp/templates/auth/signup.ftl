[#ftl]

[#assign content]

<h2>${msg['user.registered']}</h2>

<form class="form-horizontal"
      action="/auth/signup"
      method="post">

  <div class="control-group">
    <label class="control-label"
           for="inputEmail">${msg['user.email']}</label>
    <div class="controls">
      <input type="text"
             id="inputEmail"
             name="email"
             placeholder="Email">
    </div>
  </div>

  <div class="control-group">
    <label class="control-label"
           for="inputPassword">${msg['user.password']}</label>
    <div class="controls">
      <input type="text"
             id="inputPassword"
             name="pass"
             placeholder="Password">
    </div>
  </div>

  <div class="control-group">
    <label class="control-label"
           for="inputOld-password">${msg['user.password.repeat']}</label>
    <div class="controls">
      <input type="text"
             id="inputOld-password"
             name="old-pass"
             placeholder="Old password">
    </div>
  </div>

  <div class="control-group">
    <div class="control">
      <button type="submit"
              class="btn">${msg['user.button.send']}</button>
    </div>
  </div>

</form>

[/#assign]
[#include "../layout.ftl"/]