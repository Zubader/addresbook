[#ftl]

[#assign errors = flash['errors']!/]

[#assign content]
<div class="contacts form-actions">

  <address>
    <p><strong>
    ${msg['user.name']}
    </strong></p>
    <p>${contact.title}</p>
  </address>

  <hr>
  <address>
    <p><strong>
    ${msg['user.address']}
    </strong></p>
    <p>${contact.address}</p>
  </address>

  <hr>
  <address>
    <p><strong>
    ${msg['user.phonenumber']}
    </strong></p>
  ${contact.phonenumber}
  </address>

  <hr>
  <address>
    <p><strong>
    ${msg['user.comments']!}
    </strong></p>
  ${contact.comments}
  </address>

  <hr>
  <form class="form-horizontal"
        action="/contacts/${contact.id}"
        method="post">

  <table>
    [#list contacts as e]
    <tr>
      <td>
        ${e.name}
        <input type="hidden"
               name="name"
               value="${e.name}"/>
      </td>
    </tr>
    [/#list]
  </table>

  </form>
</div>
<a class="btn" href="/contacts">
  ${msg['user.button.back']}
</a>
[/#assign]

[#include "../layout.ftl"/]
