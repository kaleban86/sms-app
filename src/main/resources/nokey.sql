


select ActivationCode, DateActivation from
    last_key_activation where idKey =
         (SELECT max(idKey) from last_key_activation where idKey like  ? OR idKey like  ? )
order by DateActivation desc  limit 1