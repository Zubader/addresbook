[#ftl]

${msg['user.delete.message']}

<div class="panel">
  <address>
    <strong>${msg['user.name']!}:
    </strong><br>
  ${msg[contact.title]}
  </address>
  <hr>
  <address>
    <strong>
    ${msg['user.email']};
    </strong><br>
  ${msg[contact.address]}
  </address>
</div>

<form class="form-horizontal"
      action="/contacts/${contact.id}"
      method="post">
  <input type="hidden"
         name="_method"
         value="delete"/>
  <div class="control-group">
    <div class="controls">
      <button type="submit"
              class="btn">
      ${msg['user.button.delete']}
      </button>
    </div>
</form>